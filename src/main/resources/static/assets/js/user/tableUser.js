
document.addEventListener("DOMContentLoaded", function () {
    fetchTable();
});

async function fetchTable() {
    var restaurantId = JSON.parse(localStorage.getItem("restaurantId"));

    if (!restaurantId) {
        console.error("Không tìm thấy restaurantId trong localStorage");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/admin/restaurant/table/all/${restaurantId}`);

        if (!response.ok) {
            console.error("Không thể lấy thông tin bàn!");
            return;
        }

        const tableData = await response.json();
        console.log(tableData);
        getRestaurantDetails(restaurantId);
        displayTables(tableData);

    } catch (error) {
        console.error("Error: ", error);
    }
}
function displayTables(table) {
    var tableData = document.getElementById("tableData");
    tableData.innerHTML = "";

    table.forEach(table => {
        let formattedPrice = table.price ? table.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(table.imageTable);
        let timestamp = new Date().getTime();
        let tableDetails = `
                      <div class="table-card" data-table-id="${table.id}">
                        <div class="table-info">
                          <img src="/uploadsTable/${imageName}?t=${timestamp}" alt="${table.tableRestaurantName}">
                          <div class="table-details">
                            <h3 style="color: #000;">${table.tableRestaurantName}</h3>
                            <div style="display: flex">
                                <div style="margin-right: 20px">
                                    <p>• Sức chứa: ${table.area}</p>
                                    <p>• Tầng: ${table.tableRestaurantType}</p>
                                </div>
                                <div>
                                    <p style="font-weight: 600">• Còn lại: ${table.quantity} bàn</p>
                                </div>
                            </div>
                          </div>
                        </div>
                        <div class="table-price" style="color: #000;">
                          <span>${formattedPrice} đ/Người</span>
                          <div class="quantity-control">
                            <button class="decrease">-</button>
                            <span class="quantity">0</span>
                            <button class="increase">+</button>
                          </div>
                        </div>
                      </div>       
            `;
        tableData.innerHTML += tableDetails;
    });
    addQuantityControls();
}
// Tính tổng tiền phòng
let totalAmount = 0;
let selectedTableIds = {};
function addQuantityControls() {
    const tableCards = document.querySelectorAll(".table-card");

    tableCards.forEach((card) => {
        const decreaseBtn = card.querySelector(".decrease");
        const increaseBtn = card.querySelector(".increase");
        const quantityDisplay = card.querySelector(".quantity");
        const pricePerTable = parseInt(card.querySelector(".table-price span").textContent.replace(/[^0-9]/g, ''));
        const tableId = card.getAttribute("data-table-id");

        decreaseBtn.addEventListener("click", function () {
            let currentQuantity = parseInt(quantityDisplay.textContent);
            if (currentQuantity > 0) {
                currentQuantity -= 1;
                quantityDisplay.textContent = currentQuantity;
                updateTotalAmount(-pricePerTable);
                if (currentQuantity === 0) {
                    delete selectedTableIds[tableId];
                } else {
                    selectedTableIds[tableId] = currentQuantity;
                }
                console.log("chon table id ", selectedTableIds);
            }
        });

        increaseBtn.addEventListener("click", function () {
            let currentQuantity = parseInt(quantityDisplay.textContent);
            currentQuantity += 1;
            quantityDisplay.textContent = currentQuantity;
            updateTotalAmount(pricePerTable);
            selectedTableIds[tableId] = currentQuantity;
            console.log("chon table id", selectedTableIds);
        });
    });
}
function updateTotalAmount(amountChange) {
    totalAmount += amountChange;
    document.querySelector(".booking-summary span").textContent = `${totalAmount.toLocaleString('vi-VN')} đ`;
}


function getRestaurantDetails(restaurantId){
    var infor = document.getElementById("inforTable");
    infor.innerHTML = "";

    try{
        fetch(`http://localhost:8080/api/admin/restaurant/${restaurantId}`)
            .then(response => {
                if(!response.ok){ throw new Error("Lỗi khi lấy thông tin nhà hàng")
                }
                return response.json();
            })
            .then(data => {
                let imageName = encodeURIComponent(data.imageRestaurant);
                let timestamp = new Date().getTime();
                let formattedPrice = data.price ? data.price.toLocaleString('vi-VN') : 'N/A';
                infor.innerHTML = `
                    <div class="restaurant-info">
                        <div class="restaurant-details">
                            <div class="backRestaurant">
                                <a href="/restaurant.html" >Tìm nhà hàng</a>  >  ${data.name}
                            </div>
                            <h2 class="restaurant-name">${data.name}</h2>
                            <div class="location">
                                📍 ${data.location}
                                <a href="#" class="map-link">Xem bản đồ và địa chỉ</a>
                            </div>
                        </div>
                        <div class="price-details">
                            <div class="current-price">${formattedPrice} đ/người</div>
                        </div>
                    </div>
                    <h2 style="color:#000;">Đặc điểm nổi bật</h2>
                    <div class="features-section">
                        <div class="feature-icons">
                            <div class="box">
                                <div class="box_1">
                                    <div class="feature-item">
                                        <label for="feature1">• Không gian sang trọng</label>
                                    </div>
                                    <div class="feature-item">
                                        <label for="feature2">• Thực đơn đa dạng</label>
                                    </div>
                                    <div class="feature-item">
                                        <label for="feature3">• Wifi miễn phí</label>
                                    </div>
                                </div>
                                <div class="box_2">
                                    <div class="feature-item">
                                        <label for="feature4">• Không gian riêng tư</label>
                                    </div>
                                    <div class="feature-item">
                                        <label for="feature5">• Nguyên liệu cao cấp</label>
                                    </div>
                                    <div class="feature-item">
                                        <label for="feature6">• Phục vụ chuyên nghiệp</label>
                                    </div>
                                </div>
                            </div>
                            <div class="restaurant-info-card">
                                <h3>Thông tin nhà hàng</h3>
                                <p><span class="icon">🍽️</span> Số bàn: <strong>${data.availableTables}</strong></p>
                                <p><span class="icon">💼</span> Điều hành: <strong>Manager</strong></p>
                            </div>
                        </div>
                        <div class="feature-description">
                            <p>${data.description}</p>
                        </div>
                    </div>
                    <div style="width: 50%; margin-bottom: 50px">
                        <img class="card-img-top" src="/uploadsRestaurant/${imageName}?t=${timestamp}" alt="${data.name}">                  
                    </div>
                `
            })
    }
    catch (e) {
        console.error(e);
    }
}


// xu lys modal
document.addEventListener("DOMContentLoaded", function() {
    const modal = document.getElementById("bookingModal");
    const bookNowButton = document.querySelector(".book-now");
    const closeBtn = document.querySelector(".close-btn");

    // Mở modal khi nhấn nút "Đặt ngay"
    bookNowButton.addEventListener("click", function() {
        modal.style.display = "block";
        getTableToBill(selectedTableIds); // Example function to update the modal content
    });

    // Đóng modal khi nhấn nút "X" trong modal
    closeBtn.addEventListener("click", function() {
        modal.style.display = "none";
    });

    // Đóng modal khi nhấn bên ngoài modal
    window.addEventListener("click", function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    });
});

// lay thong tin phong duocj pick
function getTableToBill(selectTableId){
        var tableToBill = document.getElementById("tableToBill");
        tableToBill.innerHTML = "";
        let total = 0;
        const promises = [];

        Object.entries(selectTableId).forEach(([id, quantity]) => {
                const promise = fetch(`http://localhost:8080/api/admin/restaurant/table/${id}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Lỗi khi lấy thông tin nhà hàng")
                        }
                        return response.json();
                    })
                    .then(table => {
                        let formattedPrice = table.price ? table.price : 0;
                        let imageName = encodeURIComponent(table.imageTable);
                        let timestamp = new Date().getTime();
                        let tableDetails = `
                                        <div class="formTablePick">
                                          <img src="/uploadsTable/${imageName}?t=${timestamp}" alt="${table.tableRestaurantName}" class="table-image">
                                          <div class="tablePick">
                                            <div style="display: flex;flex-direction: column;">
                                                <h3>${table.tableRestaurantName}</h3>
                                                <div style="display: flex">
                                                    <div style="margin-right: 20px">
                                                        <p>• Sức chứa: ${table.area}</p>
                                                    </div>
                                                    <p>• Tầng: ${table.tableRestaurantType}</p>
                                                </div>
                                            </div>
                                            <div class="typePrice">
                                                <div class="price-per-night">
                                                    <span>${formattedPrice.toLocaleString('vi-VN')}</span> đ/Người
                                                </div>
                                                <span>• Số lượng: ${quantity}</span>
                                             </div>
                                          </div> 
                                        </div> 
                                        `;
                        let totalForTable = parseInt(formattedPrice) * quantity;
                        total += totalForTable;
                        tableToBill.innerHTML += tableDetails;

                    }).catch(e => console.error(e));
            promises.push(promise);
        });
    Promise.all(promises).then(() => {
        // Cập nhật tổng tiền sau khi tất cả API đã trả về
        document.querySelector(".total-price").textContent = total.toLocaleString('vi-VN') + " đ";
    });
}

document.addEventListener("DOMContentLoaded",  function () {
// tao moi booking
    var createBooking = document.getElementById("ConfirmBook");
    createBooking.addEventListener("click", async function () {
        var user = JSON.parse(localStorage.getItem("user"));
        console.log(user);
        const checkInDate = document.querySelector("input[name='checkInDate']").value;
        const fullName = document.querySelector("input[name='fullName']").value;
        const phoneNumber = document.querySelector("input[name='phoneNumber']").value;
        const email = document.querySelector("input[name='emailBooking']").value;
        const specialTable = document.querySelector("textarea[name='specialTable']").value;
        const total = parseFloat(document.querySelector(".totalFormBooking .total-price")
            .textContent.replace(/\D/g, ''));

        const bookingData = {
            checkInDate,
            fullName,
            phoneNumber,
            email,
            specialTable,
            user,
            tableRestaurants: selectedTableIds,
            totalAmount: total
        };
        console.log("Booking Data:", bookingData);
        if(user != null){
            try {
                const response = await fetch("http://localhost:8080/api/user/booking/new", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify(bookingData)
                });

                const data = await response.text();

                if (data === "Đặt bàn thành công!") {
                    for (let tableID in selectedTableIds) {
                        if (selectedTableIds.hasOwnProperty(tableID)) {
                            const quantityPicked = selectedTableIds[tableID];
                            const updateResponse = await fetch(`http://localhost:8080/api/admin/restaurant/table/updateQuantity/${tableID}?quantity=${quantityPicked}`, {
                                method: "PUT",
                                headers: {
                                    "Content-Type": "application/json"
                                }
                            });

                            const updateData = await updateResponse.text();

                            if (updateData !== "Cập nhật thành công") {
                                alert("Vượt quá số bàn còn tồn tại! Hãy thử lại");
                                return;
                            }
                        }
                    }
                    alert("Đặt bàn thành công!");
                    window.location.reload();
                }
            } catch (error) {
                console.error("Error:", error);
            }
        } else {
            alert("Bạn cần đăng nhập trước khi đặt bàn!");
            loginPopup.style.display = "block";
        }
    });
})
