
// Lấy thông tin tất cả chuyến bay
document.addEventListener("DOMContentLoaded", function (){
    fetchFlights() ;
    const searchForm = document.getElementById("form");
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const departureInput = document.querySelector("input[name='departure']").value;
        const arrivalInput = document.querySelector("input[name='arrival']").value;
        const dateInput = document.querySelector("input[name='date']").value;
        // tim kiem chueeyn bay
        searchFlights(departureInput, arrivalInput, dateInput);

        // Cập nhật URL với các tham số tìm kiếm
        const searchUrl = new URL(window.location);
        if (departureInput) searchUrl.searchParams.set('departure', departureInput);
        if (arrivalInput) searchUrl.searchParams.set('arrival', arrivalInput);
        if (dateInput) searchUrl.searchParams.set('date', dateInput);

        // Cập nhật lịch sử trình duyệt mà không làm mới trang
        history.pushState(null, '', searchUrl);
    });
});
async function fetchFlights() {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/flight/all`);
        if (!response.ok) {
            console.error("Không lấy được thông tin bai viets. Hãy xem lại trong article.js");
            return;
        }
        const flight = await response.json();
        console.log(flight);
        displayFlights(flight);
    } catch (error) {
        console.error("error: ", error);
    }
}
async function searchFlights(departure, arrival, date) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/flight/search?departureCity=${encodeURIComponent(departure)}&arrivalCity=${encodeURIComponent(arrival)}&departureDate=${encodeURIComponent(date)}`);
        if (!response.ok) {
            console.error("Không tìm thấy chuyến bay.");
            return;
        }
        const flights = await response.json();
        console.log(flights)
        displayFlights(flights);
    } catch (error) {
        console.error("Lỗi khi tìm kiếm chuyến bay:", error);
    }
}
function displayFlights(flight) {
    var flightData = document.getElementById("flightData");
    flightData.innerHTML = "";
    flight.forEach( flight =>{
        let formattedPrice = flight.price ? flight.price.toLocaleString('vi-VN') : 'N/A';

        let row = `
                            <tr>
                                <td>${flight.id}</td>
                                <td>${flight.flightNumber}</td>
                                <td>${flight.departureCity}</td>
                                <td>${flight.arrivalCity}</td>
                                <td>${flight.departureDate}</td>
                                <td>${flight.arrivalDate}</td>
                                <td>${flight.departureTime}</td>
                                <td>${flight.arrivalTime}</td>
                                <td>${formattedPrice}</td>
                                <td>${flight.availableSeats}</td>
                                <td>
                                    <button class="btn btn-success" onclick="editFlight(${flight.id})">Sửa</button>
                                    <button class="btn btn-danger" onclick="deleteFlight(${flight.id})">Xóa</button>
                                </td>
                            </tr>
                        `;
        flightData.innerHTML += row;
    });
}
// Hiên thị thông tin 1 chuyến bay
 function editFlight(flightId) {
     console.log(flightId);
    // Gọi API lấy thông tin chuyến bay
     fetch(`http://localhost:8080/api/admin/flight/${flightId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Không thể lấy thông tin chuyến bay');
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("flightData", JSON.stringify(data));
            // Chuyển trang hoặc mở form cập nhật
            window.location.href = "/admin/flight/update";
        })
        .catch(error => console.error("Error:", error));
}

// Xóa Chuyến bay

function deleteFlight(flightId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?");
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/flight/delete/${flightId}`, {
            method: 'DELETE'
        })
            .then(Response => {
                if (!Response.ok) {
                    throw new Error('DeleteError')
                }
                return Response.text();
            })
            .then(data => {
            console.log(data);
            if (data === "Xóa thành công") {
                window.location.reload();

            } else {
                alert("Xóa Không thành công!")
            }
        })
    }
}