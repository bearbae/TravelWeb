document.addEventListener("DOMContentLoaded", function () {
    var user = JSON.parse(localStorage.getItem("user"));
    if(user.role == 0) {
        document.getElementById("userShow").style.display = 'block';
        document.getElementById("adminShow").style.display = 'none';
        document.getElementById('user-info').style.display = 'none';
        document.getElementById('bookings-infor').style.display = 'none';
        getUser();
        getBookingHotels();
        getBookingTours();
        getBookingRestaurants();
        getBookingFlights();
    } else {
        document.getElementById("userShow").style.display = 'none';
        document.getElementById("adminShow").style.display = 'block';
        document.getElementById('bookings-section').style.display = 'none';
        document.getElementById('update-info-section').style.display = 'none';
        getAllUserForAdmin();
        getAllHotelBook();
        getAllTourBook();
        getAllRestaurantBook();
        getAllFlightBook();

    }
    // Hiển thị phần "Cập nhật thông tin"
    document.getElementById('update-info-link').addEventListener('click', function (e) {
        e.preventDefault();
        document.getElementById('update-info-section').style.display = 'block';
        document.getElementById('bookings-section').style.display = 'none';
        const sidebarLinks = document.querySelectorAll('.sidebar a');
        sidebarLinks.forEach(link => link.classList.remove('active'));
        this.classList.add('active');
    });

// Hiển thị phần "Đơn đã đặt"
    document.getElementById('bookings-link').addEventListener('click', function (e) {
        e.preventDefault();
        document.getElementById('update-info-section').style.display = 'none';
        document.getElementById('bookings-section').style.display = 'block';
        const sidebarLinks = document.querySelectorAll('.sidebar a');
        sidebarLinks.forEach(link => link.classList.remove('active'));
        this.classList.add('active');
    });

    // Hiển thị phần "Quanr ly donw"
    document.getElementById('bookings-manage').addEventListener('click', function (e) {
        e.preventDefault();
        document.getElementById('user-info').style.display = 'none';
        document.getElementById('bookings-infor').style.display = 'block';
        const sidebarLinks = document.querySelectorAll('.sidebar a');
        sidebarLinks.forEach(link => link.classList.remove('active'));
        this.classList.add('active');
    });
    // Hiển thị phần "Quan ly nguoi dung"
    document.getElementById('users-manage').addEventListener('click', function (e) {
        e.preventDefault();
        document.getElementById('bookings-infor').style.display = 'none';
        document.getElementById('user-info').style.display = 'block';
        const sidebarLinks = document.querySelectorAll('.sidebar a');
        sidebarLinks.forEach(link => link.classList.remove('active'));
        this.classList.add('active');
    });

    });


async function getUser(){
    var user = JSON.parse(localStorage.getItem("user"));
    const getUser = await fetch(`http://localhost:8080/api/user/${user.id}`);
    const response = await getUser.json();
    document.getElementById("name").value = response.name;
    document.getElementById("email").value = response.email;
    document.getElementById("phoneNumber").value = response.phoneNumber;
    document.getElementById("address").value = response.address;

    var update_info = document.getElementById("update-info-form");
    var update_password = document.getElementById("update-password-form");

    // updateInfo
    update_info.addEventListener("submit", function (e){
        e.preventDefault();
        const Message = document.getElementById("message_info");

        const formData = {
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            phoneNumber: document.getElementById("phoneNumber").value,
            address: document.getElementById("address").value
        };
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d{10}$/.test(document.getElementById("phoneNumber").value)) {
            Message.textContent = "Số điện thoại chỉ được chứa 10 ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/user/update/${user.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        }).then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
            return response.json();
        }).then(data => {
            if(data){
                document.getElementById("name").value = data.name;
                document.getElementById("email").value = data.email;
                document.getElementById("phoneNumber").value = data.phoneNumber;
                document.getElementById("address").value = data.address;
                Message.textContent = "Cập nhật thông tin thành công!";
                Message.classList.add("text-success");
                localStorage.setItem("user", JSON.stringify(data));
                setTimeout(() => {
                    window.location.reload();
                }, 3000);
            } else throw new Error("Không nhận được dữ liệu từ server") ;
        }).catch(e => {
            Message.textContent = e.message ;
            Message.classList.add("text-danger");
        });
    });

    //update password
    update_password.addEventListener("submit", function (e){
        e.preventDefault();
        const currentPassword = document.getElementById("current-password").value;
        const newPassword = document.getElementById("new-password").value;
        const renewPassword = document.getElementById("renew-password").value;
        const Message = document.getElementById("message_password");

        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if(newPassword != renewPassword){
            Message.textContent = "Mật khẩu mới không khớp. Vui lòng thử lại.";
            Message.classList.add("text-danger");
            return;
        }
        const url = `http://localhost:8080/api/user/updatePassword/${user.id}?currentPassword=${encodeURIComponent(currentPassword)}&newPassword=${encodeURIComponent(newPassword)}`;
        fetch(url, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            if (!response.ok) {
                return response.json().then(error => {
                    throw new Error(error.message);
                });
            }
            return response.json();
        }).then(data => {
            localStorage.setItem("user", JSON.stringify(data));
            Message.textContent = "Mật khẩu đã được cập nhật thành công.";
            Message.classList.add("text-success");
            setTimeout(() => {
                window.location.reload();
            }, 3000);
        }).catch(e => {
            Message.textContent = e.message;
            Message.classList.add("text-danger");
        });

    })
}


async function getBookingHotels(){
    var user  = JSON.parse(localStorage.getItem("user"));

    const response = await fetch(`http://localhost:8080/api/user/bookings/userId/${user.id}`) ;
    const dataBookHotels = await response.json() ;

    const groupedData = dataBookHotels.reduce((acc, hotel) => {
        if (!acc[hotel.idBook]) {
            acc[hotel.idBook] = {
                ...hotel,
                rooms: [],
            };
        }
        acc[hotel.idBook].rooms.push({
            name: hotel.roomName,
            quantity: hotel.roomQuantity,
        });
        return acc;
    }, {});
    const bookingHotel = document.getElementById("bookingHotel") ;
    bookingHotel.innerHTML = "";
    // dataBookHotels.forEach(hotel => {
        Object.values(groupedData).forEach(hotel => {
        const today = new Date().toISOString().split('T')[0];
        const statusText = hotel.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = hotel.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";

            let roomDetails = hotel.rooms.map(room => `- ${room.name} (${room.quantity})`).join('<br>');

            console.log(roomDetails);
            let row = `
            <tr>
                  <td>${hotel.idBook}</td>
                  <td>
                    Mã: ${hotel.idBook}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td>
                    <a onclick="viewRoom(${hotel.hotelId})">${hotel.hotelName}</a><br>
                    <span>Phòng:</span><br>${roomDetails}<br>
                    Ngày nhận phòng: ${hotel.checkInDate}
                  </td>
                  <td>
                    Tổng: ${hotel.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${hotel.totalAmount.toLocaleString()} đ
                  </td>
                  <td>
                    ${hotel.checkBrowser === 0
                    ? `<button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookHotel(${hotel.idBook})">Hủy</button>`
                    : `<button class="btn btn-secondary" disabled>Hủy</button>`}
                  </td>
            </tr>
        `;
        bookingHotel.innerHTML += row ;
    })
}

// booking tour
async function getBookingTours(){
    var user  = JSON.parse(localStorage.getItem("user"));
    const response = await fetch(`http://localhost:8080/api/user/tour/booking/userId/${user.id}`) ;
    const dataBookTours = await response.json() ;

    const bookingTour = document.getElementById("bookingTour") ;
    bookingTour.innerHTML = "";
    dataBookTours.forEach(tour => {
        const today = new Date().toISOString().split('T')[0];
        const statusText = tour.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = tour.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";
        let row = `
            <tr>
                  <td>${tour.idBookTour}</td>
                  <td>
                    Mã: ${tour.idBookTour}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td>
                    <a onclick="viewService(${tour.idTour})">${tour.nameTour}</a><br>
                    Ngày nhận tour: ${tour.dateBook}
                  </td>
                  <td>
                    Tổng: ${tour.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${tour.totalAmount.toLocaleString()} đ
                  </td>
                  <td>
                    ${tour.checkBrowser === 0
                    ? `<button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookTour(${tour.idBookTour})">Hủy</button>`
                    : `<button class="btn btn-secondary" disabled>Hủy</button>`}
                  </td>
            </tr>
        `;
        bookingTour.innerHTML += row ;
    })
}
async function getBookingRestaurants(){
    var user  = JSON.parse(localStorage.getItem("user"));

    const response = await fetch(`http://localhost:8080/api/user/booking/userId/${user.id}`) ;
    const dataBookRestaurants = await response.json() ;

    const groupedData = dataBookRestaurants.reduce((acc, restaurant) => {
        if (!acc[restaurant.idBook]) {
            acc[restaurant.idBook] = {
                ...restaurant,
                tables: [],
            };
        }
        acc[restaurant.idBook].tables.push({
            tableName: restaurant.tableRestaurantName,
            quantity: restaurant.tableQuantity
        });
        return acc;
    }, {});
    const bookingRestaurant = document.getElementById("bookingRestaurant") ;
    bookingRestaurant.innerHTML = "";
    // dataBookRestaurants.forEach(restaurant => {
    Object.values(groupedData).forEach(restaurant => {
        const today = new Date().toISOString().split('T')[0];
        const statusText = restaurant.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = restaurant.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";
        let tableDetails = restaurant.tables.map(table => `- ${table.tableName} (${table.quantity})`).join('<br>');
        let row = `
            <tr>
                  <td>${restaurant.idBook}</td>
                  <td>
                    Mã: ${restaurant.idBook}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td>
                    <a onclick="viewTable(${restaurant.restaurantId})">${restaurant.restaurantName}</a><br>
                    <span>Tên bàn</span><br>
                        ${tableDetails}<br>
                    Ngày nhận bàn: ${restaurant.checkInDate}
                  </td>
                  <td>
                    Tổng: ${restaurant.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${restaurant.totalAmount.toLocaleString()} đ
                  </td>
                  <td>
                    ${restaurant.checkBrowser === 0
            ? `<button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookRestaurant(${restaurant.idBook})">Hủy</button>`
            : `<button class="btn btn-secondary" disabled>Hủy</button>`}
                  </td>
            </tr>
        `;
        bookingRestaurant.innerHTML += row ;
    })
}

// ve may bay
async function getBookingFlights(){
    var user  = JSON.parse(localStorage.getItem("user"));
    const response = await fetch(`http://localhost:8080/api/user/bookings-flight/userId/${user.id}`) ;
    const dataBookFlights = await response.json() ;

    const bookingFlight = document.getElementById("bookingFlight") ;
    bookingFlight.innerHTML = "";
    dataBookFlights.forEach(flight => {
        const today = new Date().toISOString().split('T')[0];
        const statusText = flight.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = flight.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";
        let row = `
            <tr>
                  <td>${flight.idBook}</td>
                  <td>
                    Mã: ${flight.idBook}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td>
                    ${flight.nameDepartureCity} → ${flight.nameArrivalCity}<br>
                    Ngày khởi hành: ${flight.departureDateBook}<br>
                  </td>
                  <td>
                    Tổng: ${flight.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${flight.totalAmount.toLocaleString()} đ
                  </td>
                  <td>
                    ${flight.checkBrowser === 0
            ? `<button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookFlight(${flight.idBook})">Hủy</button>`
            : `<button class="btn btn-secondary" disabled>Hủy</button>`}
                  </td>
            </tr>
        `;
        bookingFlight.innerHTML += row ;
    })
}
function viewRoom(hotelId) {
    localStorage.setItem("hotelId", JSON.stringify(hotelId));
    window.location.href = "/user/room";
}
 function viewService(idTour){
    localStorage.setItem("idTour", JSON.stringify(idTour));
    window.location.href = "/user/servicePackage";
}
function viewTable(restaurantId) {
    localStorage.setItem("restaurantId", JSON.stringify(restaurantId));
    window.location.href = "/user/room";
}

/// adminnnn

async function getAllUserForAdmin(){
    var user_data = document.getElementById("user-data");
    const response = await fetch(`http://localhost:8080/api/user/all`) ;
    const data_response = await response.json() ;
    user_data.innerHTML = "" ;
    data_response.forEach(data => {
        const nameRole = data.role === 1 ? "admin" : "user";
        let adminButton = `
            <button class="btn admin" onclick="setRole('${nameRole}', ${data.id})" ${nameRole === "admin" ? 'disabled' : ''}>Admin</button>
        `;
        let userButton = `
            <button class="btn user" onclick="setRole('${nameRole}', ${data.id})" ${nameRole === "user" ? 'disabled' : ''}>User</button>
        `;
        let userRow = `
            <tr>
                  <td>${data.id}</td>
                  <td>${data.name}</td>
                  <td>${data.email}</td>
                  <td>${data.address}</td>
                  <td>${data.phoneNumber}</td>
                  <td>${nameRole}</td>
                  <td>
                      ${adminButton}
                      ${userButton}
                  </td>
            </tr>
        `;
        user_data.innerHTML += userRow ;
    })
}

async function getAllHotelBook(){
    var bookingHotel_data = document.getElementById("bookingHotel-data") ;
    const response = await fetch(`http://localhost:8080/api/user/bookings/all`) ;
    const dataBookToursAdmin = await response.json() ;

    const groupedDataAdmin = dataBookToursAdmin.reduce((acc, hotel) => {
        if (!acc[hotel.idBook]) {
            acc[hotel.idBook] = {
                ...hotel,
                rooms: [],
            };
        }
        acc[hotel.idBook].rooms.push({
            name: hotel.roomName,
            quantity: hotel.roomQuantity,
        });
        return acc;
    }, {});
    bookingHotel_data.innerHTML = "";
    // dataBookToursAdmin.forEach(data => {
        Object.values(groupedDataAdmin).forEach(data => {
        const today = new Date().toISOString().split('T')[0];
        console.log(data.checkBrowser);
        const statusText = data.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = data.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";

            let roomDetails = data.rooms.map(room => `- ${room.name} (${room.quantity})`).join('<br>');
        let row = `
            <tr>
                  <td>${data.idBook}</td>
                  <td>
                    Mã: ${data.idBook}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td>
                    Tên: ${data.nameUser}<br>
                    Email: ${data.email}<br>
                    Số điện thoại: ${data.phoneNumber}<br>
                    Yêu cầu: ${data.specialRequest}<br>
                    
                  </td>
                  <td>
                    <a onclick="viewRoom(${data.hotelId})">${data.hotelName}</a><br>
                    <span>Phòng:</span><br>${roomDetails}<br>
                    Ngày nhận phòng: ${data.checkInDate}
                  </td>
                  <td>
                    Tổng: ${data.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${data.totalAmount.toLocaleString()} đ
                  </td>
                  <td style=" width: 170px;"> 
                    ${data.checkBrowser === 0
                    ? `<button class="btn btn-success approveBook" onclick="approveBookingHotel(this, ${data.idBook})">Duyệt</button>`
                    : `<button class="btn btn-secondary" disabled>Đã duyệt</button>`}
                    <button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookHotel(${data.idBook})">Hủy</button>               
                  </td>
            </tr>
        `;
        bookingHotel_data.innerHTML += row ;
    })

}

async function getAllTourBook(){
    var bookingTour_data = document.getElementById("bookingTour-data");
    const response = await fetch(`http://localhost:8080/api/user/tour/booking/all`) ;
    const dataBookToursAdmin = await response.json() ;
    bookingTour_data.innerHTML = "";
    dataBookToursAdmin.forEach(data => {
        const today = new Date().toISOString().split('T')[0];
        const statusText = data.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = data.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";
        console.log(statusClass, statusText);
        let row = `
            <tr>
                  <td>${data.idBookTour}</td>
                  <td>
                    Mã: ${data.idBookTour}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td style="width: 300px">
                    Tên: ${data.nameUser}<br>
                    Email: ${data.email}<br>
                    Số điện thoại: ${data.phoneNumber}<br>
                    Yêu cầu: ${data.specialRequest}<br>
                  </td>
                  <td style="width: 370px">
                    <a onclick="viewService(${data.idTour})">${data.nameTour}</a><br>
                    Ngày nhận tour: ${data.dateBook}
                  </td>
                  <td>
                    Tổng: ${data.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${data.totalAmount.toLocaleString()} đ
                  </td>
             
                  <td style="width: 170px;">
                    ${data.checkBrowser === 0
                        ? `<button class="btn btn-success approveBook" onclick="approveBookingTour(this, ${data.idBookTour})">Duyệt</button>`
                        : `<button class="btn btn-secondary" disabled>Đã duyệt</button>`}
                    <button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookTour(${data.idBookTour})">Hủy</button>               
                  </td>
            </tr>
        `;
        bookingTour_data.innerHTML += row ;
    })
}
async function getAllRestaurantBook(){
    var bookingRestaurant_data = document.getElementById("bookingRestaurant-data") ;
    const response = await fetch(`http://localhost:8080/api/user/booking/all`) ;
    const dataBookToursAdmin = await response.json() ;

    const groupedDataRestaurantAdmin = dataBookToursAdmin.reduce((acc, restaurant) => {
        if (!acc[restaurant.idBook]) {
            acc[restaurant.idBook] = {
                ...restaurant,
                tables: [],
            };
        }
        acc[restaurant.idBook].tables.push({
            tableName: restaurant.tableRestaurantName,
            quantity: restaurant.tableQuantity
        });
        return acc;
    }, {});
    bookingRestaurant_data.innerHTML = "";
    // dataBookToursAdmin.forEach(data => {
    Object.values(groupedDataRestaurantAdmin).forEach(data => {
        const today = new Date().toISOString().split('T')[0];
        console.log(data.checkBrowser);
        const statusText = data.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = data.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";

        let tableDetails = data.tables.map(table => `- ${table.tableName} (${table.quantity})`).join('<br>');

        let row = `
            <tr>
                  <td>${data.idBook}</td>
                  <td>
                    Mã: ${data.idBook}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td>
                    Tên: ${data.nameUser}<br>
                    Email: ${data.email}<br>
                    Số điện thoại: ${data.phoneNumber}<br>
                    Yêu cầu: ${data.specialTable}<br>

                  </td>
                  <td>
                    <a onclick="viewTable(${data.restaurantId})">${data.restaurantName}</a><br>
                    <span>Tên bàn</span><br>
                        ${tableDetails}<br>
                    Ngày nhận bàn: ${data.checkInDate}
                  </td>
                  <td>
                    Tổng: ${data.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${data.totalAmount.toLocaleString()} đ
                  </td>
                  <td style="width: 170px;">
                    ${data.checkBrowser === 0
                    ? `<button class="btn btn-success approveBook" onclick="approveBookingRestaurant(this, ${data.idBook})">Duyệt</button>`
                    : `<button class="btn btn-secondary" disabled>Đã duyệt</button>`}
                    <button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookRestaurant(${data.idBook})">Hủy</button>               
                  </td>
            </tr>
        `;
        bookingRestaurant_data.innerHTML += row ;
    })

}

async function getAllFlightBook(){
    var bookingFlight_data = document.getElementById("bookingFlight-data");
    const response = await fetch(`http://localhost:8080/api/user/bookings-flight/all`) ;
    const dataBookToursAdmin = await response.json() ;
    bookingFlight_data.innerHTML = "";
    dataBookToursAdmin.forEach(data => {
        const today = new Date().toISOString().split('T')[0];
        const statusText = data.checkBrowser === 1 ? "Đã duyệt" : "Đang duyệt";
        const statusClass = data.checkBrowser === 1 ? "badge bg-success" : "badge bg-info";
        console.log(statusClass, statusText);
        let row = `
            <tr>
                  <td>${data.idBook}</td>
                  <td>
                    Mã: ${data.idBook}<br>
                    Ngày đặt: ${today}<br>
                    Trạng thái: <span class="${statusClass} status">${statusText}</span>
                  </td>
                  <td style="width: 300px">
                    Tên: ${data.nameUser}<br>
                    Email: ${data.email}<br>
                    Số điện thoại: ${data.phoneNumber}<br>
                  </td>
                  <td style="width: 370px">
                    ${data.nameDepartureCity}<br>
                    Ngày khởi hành: ${data.departureDateBook}
                  </td>
                  <td>
                    Tổng: ${data.totalAmount.toLocaleString()} đ<br>
                    Đã T/Toán: 0<br>
                    Còn thiếu: ${data.totalAmount.toLocaleString()} đ
                  </td>

                  <td style="width: 170px;">
                    ${data.checkBrowser === 0
                    ? `<button class="btn btn-success approveBook" onclick="approveBookingFlight(this, ${data.idBook})">Duyệt</button>`
                    : `<button class="btn btn-secondary" disabled>Đã duyệt</button>`}
                    <button class="btn btn-danger" style="background-color: #ed5f5f" onclick="deleteBookFlight(${data.idBook})">Hủy</button>               
                  </td>
            </tr>
        `;
        bookingFlight_data.innerHTML += row ;
    })
}

function deleteBookHotel(idBook){
    let confirmation = confirm("Bạn có chắc chắn muốn hủy không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/user/bookings/delete/${idBook}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa Thành công!") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}
function deleteBookTour(idBookTour){
    let confirmation = confirm("Bạn có chắc chắn muốn hủy không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/user/tour/booking/delete/${idBookTour}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa Thành công!") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}
function deleteBookRestaurant(idBook){
    let confirmation = confirm("Bạn có chắc chắn muốn hủy không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/user/booking/delete/${idBook}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa Thành công!") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}
function deleteBookFlight(idBook){
    let confirmation = confirm("Bạn có chắc chắn muốn hủy không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/user/bookings-flight/delete/${idBook}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xóa Thành công!") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}

async function approveBookingTour(button, bookingId) {
        try {
            const response = await fetch(`http://localhost:8080/api/user/tour/booking/approve/${bookingId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.ok) {
                const statusElement = button.closest('tr').querySelector('.status');
                if (statusElement) {
                    statusElement.textContent = "Đã duyệt";
                    statusElement.classList.remove('bg-info');
                    statusElement.classList.add('bg-success');
                }

                // Vô hiệu hóa nút
                button.disabled = true;
                button.textContent = "Đã duyệt";
            } else {
                console.error('Cập nhật thất bại:', await response.text());
                alert('Cập nhật trạng thái thất bại!');
            }
        } catch (error) {
            console.error('Lỗi khi gọi API:', error);
            alert('Đã xảy ra lỗi!');
        }
}
async function approveBookingHotel(button, bookingId) {
    try {
        const response = await fetch(`http://localhost:8080/api/user/bookings/approve/${bookingId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' }
        });

        if (response.ok) {
            const statusElement = button.closest('tr').querySelector('.status');
            if (statusElement) {
                statusElement.textContent = "Đã duyệt";
                statusElement.classList.remove('bg-info');
                statusElement.classList.add('bg-success');
            }

            // Vô hiệu hóa nút
            button.disabled = true;
            button.textContent = "Đã duyệt";
        } else {
            console.error('Cập nhật thất bại:', await response.text());
            alert('Cập nhật trạng thái thất bại!');
        }
    } catch (error) {
        console.error('Lỗi khi gọi API:', error);
        alert('Đã xảy ra lỗi!');
    }
}

async function approveBookingRestaurant(button, bookingId) {
    try {
        const response = await fetch(`http://localhost:8080/api/user/booking/approve/${bookingId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' }
        });

        if (response.ok) {
            const statusElement = button.closest('tr').querySelector('.status');
            if (statusElement) {
                statusElement.textContent = "Đã duyệt";
                statusElement.classList.remove('bg-info');
                statusElement.classList.add('bg-success');
            }

            // Vô hiệu hóa nút
            button.disabled = true;
            button.textContent = "Đã duyệt";
        } else {
            console.error('Cập nhật thất bại:', await response.text());
            alert('Cập nhật trạng thái thất bại!');
        }
    } catch (error) {
        console.error('Lỗi khi gọi API:', error);
        alert('Đã xảy ra lỗi!');
    }
}
async function approveBookingFlight(button, bookingId) {
    try {
        const response = await fetch(`http://localhost:8080/api/user/bookings-flight/approve/${bookingId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' }
        });

        if (response.ok) {
            const statusElement = button.closest('tr').querySelector('.status');
            if (statusElement) {
                statusElement.textContent = "Đã duyệt";
                statusElement.classList.remove('bg-info');
                statusElement.classList.add('bg-success');
            }

            // Vô hiệu hóa nút
            button.disabled = true;
            button.textContent = "Đã duyệt";
        } else {
            console.error('Cập nhật thất bại:', await response.text());
            alert('Cập nhật trạng thái thất bại!');
        }
    } catch (error) {
        console.error('Lỗi khi gọi API:', error);
        alert('Đã xảy ra lỗi!');
    }
}
async function setRole(nameRole, userId){
    if(nameRole === "user"){
        try {
            const response = await fetch(`http://localhost:8080/api/user/roleAdmin/${userId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.ok) {
                alert('Chuyển admin thành công!');
                const  data = await response.json() ;
                // localStorage.setItem("user", JSON.stringify(data)) ;
                window.location.reload() ;
            } else {
                console.error('Cập nhật thất bại:', await response.text());
                alert('Cập nhật admin thất bại!');
            }
        } catch (error) {
            console.error('Lỗi khi gọi API:', error);
            alert('Đã xảy ra lỗi!');
        }
    } else {
        try {
            const response = await fetch(`http://localhost:8080/api/user/roleUser/${userId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' }
            });

            if (response.ok) {
                alert('Chuyển user thành công!');
                const  data = await response.json() ;
                // localStorage.setItem("user", JSON.stringify(data)) ;
                window.location.reload() ;
            } else {
                console.error('Cập nhật thất bại:', await response.text());
                alert('Cập nhật user thất bại!');
            }
        } catch (error) {
            console.error('Lỗi khi gọi API:', error);
            alert('Đã xảy ra lỗi!');
        }
    }
}