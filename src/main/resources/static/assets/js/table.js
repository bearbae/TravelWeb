
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
        getNameRestaurant(restaurantId);
        displayTables(tableData);
    } catch (error) {
        console.error("Error: ", error);
    }
}

function displayTables(tableRestaurant) {
    var tableData = document.getElementById("tableData");
    tableData.innerHTML = "";

    tableRestaurant.forEach(tableRestaurant => {
        let formattedPrice = tableRestaurant.price ? tableRestaurant.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(tableRestaurant.imageTable);
        let timestamp = new Date().getTime();
        let row = `
                    <tr>
                        <td>${tableRestaurant.id}</td>
                        <td>${tableRestaurant.tableRestaurantName}</td>
                        <td>${tableRestaurant.tableRestaurantType}</td>
                        <td>${tableRestaurant.area}</td>
                        <td>${formattedPrice}</td>
                        <td>${tableRestaurant.amenities}</td>
                        <td style="width: 200px;"><img src="/uploadsTable/${imageName}?t=${timestamp}" alt="${tableRestaurant.tableRestaurantName}" style="max-width: 100%;height: auto; object-fit: cover;" width="200" /></td>
                        <td>${tableRestaurant.quantity}</td>
                        <td>
                            <button class="btn btn-success" onclick="editTable(${tableRestaurant.id})">Sửa</button>
                            <button class="btn btn-danger" onclick="deleteTable(${tableRestaurant.id})">Xóa</button>
                        </td>
                    </tr>
            `;
        tableData.innerHTML += row;
    });
}
function getNameRestaurant(restaurantId){
    var nameRestaurant = document.getElementById("nameRestaurant");
    nameRestaurant.innerHTML= "";
    try{
        fetch(`http://localhost:8080/api/admin/restaurant/${restaurantId}`)
            .then(response => {
                if(!response.ok){ throw new Error("Lỗi khi lấy thông tin nhà hàng")
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                nameRestaurant.innerHTML = `<h1>${data.name}</h1>`;
            })
    }
    catch (e) {
        console.error(e);
    }
}

function editTable(tableId){
    fetch(`http://localhost:8080/api/admin/restaurant/table/${tableId}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin bàn!";
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("table", JSON.stringify(data));
            window.location.href = "/admin/restaurant/table/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteTable(tableId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/restaurant/table/delete/${tableId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa thành công!") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}