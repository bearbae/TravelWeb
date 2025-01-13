
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
        getNameHotel(hotelId);
        // Hiển thị dữ liệu phòng
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
        let row = `
                    <tr>
                        <td>${room.id}</td>
                        <td>${room.roomName}</td>
                        <td>${room.roomType}</td>
                        <td>${room.view}</td>
                        <td>${room.area}</td>
                        <td>${formattedPrice}</td>
                        <td>${room.amenities}</td>
                        <td><img src="/uploadsRoom/${imageName}?t=${timestamp}" alt="${room.roomName}" style="max-width: 100%;height: auto; object-fit: cover;" width="200" /></td>
                        <td>${room.quantity}</td>
                        <td>
                            <button class="btn btn-success" onclick="editRoom(${room.id})">Sửa</button>
                            <button class="btn btn-danger" onclick="deleteRoom(${room.id})">Xóa</button>
                        </td>
                    </tr>
            `;
        roomData.innerHTML += row;
    });
}

// lấy tên khách sạn đẻ hiển thị
function getNameHotel(hotelId){
    var nameHotel = document.getElementById("nameHotel");
    nameHotel.innerHTML= "";
    try{
        fetch(`http://localhost:8080/api/admin/hotel/${hotelId}`)
            .then(response => {
                if(!response.ok){ throw new Error("Lỗi khi lấy thông tin khách sạn")
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                nameHotel.innerHTML = `<h1>${data.name}</h1>`;
            })
    }
    catch (e) {
        console.error(e);
    }
}

// Cập nhất phong
function editRoom(roomId){
    fetch(`http://localhost:8080/api/admin/hotel/room/${roomId}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin phong!";
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("room", JSON.stringify(data));
            window.location.href = "/admin/hotel/room/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteRoom(roomId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/hotel/room/delete/${roomId}`, {
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