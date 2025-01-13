
document.addEventListener("DOMContentLoaded", function () {
    fetchRoom();
});

async function fetchRoom() {
    var hotelId = JSON.parse(localStorage.getItem("hotelId"));

    if (!hotelId) {
        console.error("Không tìm thấy hotelId trong localStorage");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/admin/hotel/room/all/${hotelId}`);

        if (!response.ok) {
            console.error("Không thể lấy thông tin phòng!");
            return;
        }

        const roomData = await response.json();
        // getNameHotel(hotelId);
        // Hiển thị dữ liệu phòng
        console.log(roomData);
        getHotelDetails(hotelId);
        displayRooms(roomData);

    } catch (error) {
        console.error("Error: ", error);
    }
}
function displayRooms(room) {
    var roomData = document.getElementById("roomData");
    roomData.innerHTML = ""; // Xóa nội dung cũ nếu có

    room.forEach(room => {
        let formattedPrice = room.pricePerNight ? room.pricePerNight.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(room.imageRoom);
        let timestamp = new Date().getTime();
        let roomDetails = `
                      <div class="room-card" data-room-id="${room.id}">
                        <div class="room-info">
                          <img src="/uploadsRoom/${imageName}?t=${timestamp}" alt="${room.roomName}">
                          <div class="room-details">
                            <h3>${room.roomName}</h3>
                            <div style="display: flex">
                                <div style="margin-right: 20px">
                                    <p>·Diện tích: ${room.area}</p>
                                    <p>·Hướng: ${room.view}</p>
                                </div>
                                <div>
                                    <p>·Loại phòng: ${room.roomType}</p>
                                    <p style="font-weight: 600">·Còn: ${room.quantity} phòng</p>
                                </div>
                                <div style="margin-left: 30px">
                                    <p>${room.amenities}</p>
                                </div>
                            </div>
                          </div>
                        </div>
                        <div class="room-price">
                          <span>${formattedPrice} đ/PHÒNG</span>
                          <div class="quantity-control">
                            <button class="decrease">-</button>
                            <span class="quantity">0</span>
                            <button class="increase">+</button>
                          </div>
                        </div>
                      </div>       
            `;
        roomData.innerHTML += roomDetails;
    });
    addQuantityControls();
}
// Tính tổng tiền phòng
let totalAmount = 0;
let selectedRoomIds = {};
function addQuantityControls() {
    const roomCards = document.querySelectorAll(".room-card");

    roomCards.forEach((card) => {
        const decreaseBtn = card.querySelector(".decrease");
        const increaseBtn = card.querySelector(".increase");
        const quantityDisplay = card.querySelector(".quantity");
        const pricePerRoom = parseInt(card.querySelector(".room-price span").textContent.replace(/[^0-9]/g, ''));
        const roomId = card.getAttribute("data-room-id");

        decreaseBtn.addEventListener("click", function () {
            let currentQuantity = parseInt(quantityDisplay.textContent);
            if (currentQuantity > 0) {
                currentQuantity -= 1;
                quantityDisplay.textContent = currentQuantity;
                updateTotalAmount(-pricePerRoom);
                if (currentQuantity === 0) {
                    // selectedRoomIds = selectedRoomIds.filter(id => id !== roomId);
                    delete selectedRoomIds[roomId];
                } else {
                    selectedRoomIds[roomId] = currentQuantity;
                }
                console.log("chon room id ", selectedRoomIds);
            }
        });

        increaseBtn.addEventListener("click", function () {
            let currentQuantity = parseInt(quantityDisplay.textContent);
            currentQuantity += 1;
            quantityDisplay.textContent = currentQuantity;
            updateTotalAmount(pricePerRoom);
            // if (currentQuantity >= 1 && !selectedRoomIds.includes(roomId)) {
            //     selectedRoomIds.push(roomId);
            // }
            selectedRoomIds[roomId] = currentQuantity;
            console.log("chon room id", selectedRoomIds);
        });
    });
}
function updateTotalAmount(amountChange) {
    totalAmount += amountChange;
    document.querySelector(".booking-summary span").textContent = `${totalAmount.toLocaleString('vi-VN')} đ`;
}

// lấy chi tiết thong tin phòng
function getHotelDetails(hotelId){
    var infor = document.getElementById("inforRoom");
    infor.innerHTML = "";

    try{
        fetch(`http://localhost:8080/api/admin/hotel/${hotelId}`)
            .then(response => {
                if(!response.ok){ throw new Error("Lỗi khi lấy thông tin khách sạn")
                }
                return response.json();
            })
            .then(data => {
                let imageName = encodeURIComponent(data.imageHotel);
                let timestamp = new Date().getTime();
                let formattedPrice = data.price ? data.price.toLocaleString('vi-VN') : 'N/A';
                infor.innerHTML = `
                    <div class="hotel-info">
                        <div class="hotel-details">
                            <div class="backHotel">
                                <a href="/user/hotel" >Tìm khách sạn</a>  >  ${data.name}
                            </div>
                            <h2 class="hotel-name">${data.name}</h2>
                            <div class="location">
                                📍 ${data.location}
                                <a href="#" class="map-link">Xem bản đồ và địa chỉ</a>
                            </div>
                        </div>
                        <div class="price-details">
                            <div class="current-price">${formattedPrice} đ/phòng</div>
                        </div>
                    </div>
                    <h2>Đặc điểm nổi bật</h2>
                    <div class="features-section">
                        <div class="feature-icons">
                            <div class="box">
                                <div class="box_1">
                                    <div class="feature-item">
                                        <input type="radio" id="feature1">
                                        <label for="feature1">Phòng có bồn tắm</label>
                                    </div>
                                    <div class="feature-item">
                                        <input type="radio" id="feature2">
                                        <label for="feature2">Lễ tân 24h</label>
                                    </div>
                                    <div class="feature-item">
                                        <input type="radio" id="feature3">
                                        <label for="feature3">Wifi miễn phí</label>
                                    </div>
                                </div>
                                <div class="box_2">
                                    <div class="feature-item">
                                        <input type="radio" id="feature4">
                                        <label for="feature4">Nhà hàng</label>
                                    </div>
                                    <div class="feature-item">
                                        <input type="radio" id="feature5">
                                        <label for="feature5">Ban công/Cửa sổ</label>
                                    </div>
                                    <div class="feature-item">
                                        <input type="radio" id="feature6">
                                        <label for="feature6">Bể bơi ngoài trời</label>
                                    </div>
                                </div>
                            </div>
                            <div class="hotel-info-card">
                                <h3>Thông tin khách sạn</h3>
                                <p><span class="icon">🛏️</span> Số phòng: <strong>${data.availableRooms}</strong></p>
                                <p><span class="icon">💼</span> Điều hành: <strong>Anh Tiến</strong></p>
                            </div>
                        </div>
                        <div class="feature-description">
                            <p>${data.description}</p>
                        </div>
                    </div>
                    <div style="width: 50%; margin-bottom: 50px">
                        <img class="card-img-top" src="/uploadsHotel/${imageName}?t=${timestamp}" alt="${data.name}">                  
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
        getRoomToBill(selectedRoomIds);
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
function getRoomToBill(selectRoomId){
        var roomToBill = document.getElementById("roomToBill");
        roomToBill.innerHTML = "";
        let total = 0;
        const promises = [];

        Object.entries(selectRoomId).forEach(([id, quantity]) => {
                const promise = fetch(`http://localhost:8080/api/admin/hotel/room/${id}`)
                    .then(response => {
                        if (!response.ok) {
                            throw new Error("Lỗi khi lấy thông tin khách sạn")
                        }
                        return response.json();
                    })
                    .then(room => {
                        let formattedPrice = room.pricePerNight ? room.pricePerNight : 0;
                        let imageName = encodeURIComponent(room.imageRoom);
                        let timestamp = new Date().getTime();
                        let roomDetails = `
                                        <div class="formRoomPick">
                                          <img src="/uploadsRoom/${imageName}?t=${timestamp}" alt="${room.roomName}" class="room-image">
                                          <div class="roomPick">
                                            <div style="display: flex;flex-direction: column;">
                                                <h3>${room.roomName}</h3>
                                                <div style="display: flex">
                                                    <div style="margin-right: 20px">
                                                        <p>·Diện tích: ${room.area}</p>
                                                        <p>·Vị trí: ${room.view}</p>
                                                    </div>
                                                    <p>·Loại phòng: ${room.roomType}</p>
                                                </div>
                                            </div>
                                            <div class="typePrice">
                                                <div class="price-per-night">
                                                    <span>${formattedPrice.toLocaleString('vi-VN')}</span> đ/Phòng
                                                </div>
                                                <span>Số lượng: ${quantity}</span>
                                             </div>
                                          </div> 
                                        </div> 
                                        `;
                        let totalForRoom = parseInt(formattedPrice) * quantity;
                        total += totalForRoom;
                        roomToBill.innerHTML += roomDetails;

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
    var createBooking = document.getElementById("ConfirmBookHotel");
    createBooking.addEventListener("click", async function () {
        var user = JSON.parse(localStorage.getItem("user"));
        console.log(user);
        const checkInDate = document.querySelector("input[name='checkInDate']").value;
        const fullName = document.querySelector("input[name='fullName']").value;
        const phoneNumber = document.querySelector("input[name='phoneNumber']").value;
        const email = document.querySelector("input[name='emailBooking']").value;
        const specialRequest = document.querySelector("textarea[name='specialRequest']").value;
        const total = parseFloat(document.querySelector(".totalFormBooking .total-price")
            .textContent.replace(/\D/g, ''));

        const bookingData = {
            checkInDate,
            fullName,
            phoneNumber,
            email,
            specialRequest,
            user,
            rooms: selectedRoomIds,
            totalAmount: total
        };
        if(total != 0) {
            if (user != null) {
                try {
                    const response = await fetch("http://localhost:8080/api/user/bookings/new", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(bookingData)
                    });

                    const data = await response.text();

                    if (data === "Đặt phòng thành công!") {
                        for (let roomID in selectedRoomIds) {
                            if (selectedRoomIds.hasOwnProperty(roomID)) {
                                const quantityPicked = selectedRoomIds[roomID];
                                const updateResponse = await fetch(`http://localhost:8080/api/admin/hotel/room/updateQuantity/${roomID}?quantity=${quantityPicked}`, {
                                    method: "PUT",
                                    headers: {
                                        "Content-Type": "application/json"
                                    }
                                });

                                const updateData = await updateResponse.text();

                                if (updateData !== "Cập nhật thành công") {
                                    alert("Vượt quá số phòng còn tồn tại! Hãy thử lại");
                                    return;
                                }
                            }
                        }
                        BookHotelSuccess(true);
                        // alert("Đặt phòng thành công!");
                    }
                } catch (error) {
                    console.error("Error:", error);
                }
            } else {
                alert("Bạn cần đăng nhập trước khi đặt phòng!");
                loginPopup.style.display = "block";
            }
        } else {
            BookHotelSuccess(false);
            // alert("Bạn chưa chọn phòng!");
        }
    });
})

// modal success
function BookHotelSuccess(isSuccess){
    const alertModal = document.getElementById('alertModal');
    const closeModalBtn = document.getElementById('closeModalBtn');
    const successDiv = document.getElementById('success');
    const failDiv = document.getElementById('fail');

    alertModal.style.display = 'flex';
    if (isSuccess) {
        successDiv.style.display = 'block';
        failDiv.style.display = 'none';

        closeModalBtn.addEventListener('click', () => {
            alertModal.style.display = 'none';
            window.location.href = "/user/room";

        });

        window.addEventListener('click', (event) => {
            if (event.target === alertModal) {
                alertModal.style.display = 'none';
                window.location.href = "/user/room";
            }
        });
    } else {
        successDiv.style.display = 'none';
        failDiv.style.display = 'block';

        closeModalBtn.addEventListener('click', () => {
            alertModal.style.display = 'none';
        });

        window.addEventListener('click', (event) => {
            if (event.target === alertModal) {
                alertModal.style.display = 'none';
            }
        });
    }

}