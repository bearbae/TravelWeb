document.addEventListener("DOMContentLoaded", function () {
    // Lấy dữ liệu chuyến bay từ localStorage
    var flightData = JSON.parse(localStorage.getItem("flightData"));
    console.log(flightData);
    let formattedPrice = flightData.price ? flightData.price.toLocaleString('vi-VN'): 'N/A';

    if (flightData) {
        // Điền thông tin chuyến bay vào form
        document.getElementById("Flight_number").value = flightData.flightNumber;
        document.getElementById("Departure_city").value = flightData.departureCity;
        document.getElementById("Arrival_city").value = flightData.arrivalCity;
        document.getElementById("Departure_date").value = flightData.departureDate;
        document.getElementById("Arrival_date").value = flightData.arrivalDate;
        document.getElementById("Departure_time").value = flightData.departureTime;
        document.getElementById("Arrival_time").value = flightData.arrivalTime;
        document.getElementById("Price").value = formattedPrice;
        document.getElementById("Available_seats").value = flightData.availableSeats;
    }

    // Cập nhật thông tin chuyến bay
    var updateFlight = document.getElementById("updateFlight");
    updateFlight.addEventListener("submit", function (even) {
        even.preventDefault();
        var Data = {
            flightNumber: document.getElementById("Flight_number").value,
            departureCity: document.getElementById("Departure_city").value,
            arrivalCity: document.getElementById("Arrival_city").value,
            departureDate: document.getElementById("Departure_date").value,
            arrivalDate: document.getElementById("Arrival_date").value,
            departureTime: document.getElementById("Departure_time").value,
            arrivalTime: document.getElementById("Arrival_time").value,
            price: document.getElementById("Price").value,
            availableSeats: document.getElementById("Available_seats").value
        };
        console.log(Data);
        console.log(flightData.id);
        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("Available_seats").value)) {
            Message.textContent = "Ghế trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/flight/update/${flightData.id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(Data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error");
                }
                return response.json();
            })
            .then(data => {
                if (data) {
                    alert("Cập nhật thành công!");
                    window.location.href = "/admin/flight";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Cập nhật thất bại");
            })
    })
})



