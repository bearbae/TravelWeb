document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 0;
    const itemsPerPage = 9;
    let currentNameHotel = "";
    let currentLocation = "";
    let currentPriceHotel = "allPrice";

    fetchHotelUser(currentPage, itemsPerPage);

    const searchForm = document.getElementById("form");
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn reload trang khi submit
         currentNameHotel = document.querySelector("input[name='nameHotel']").value;
         currentLocation = document.querySelector("input[name='location']").value;
         currentPriceHotel = document.querySelector("select[name='priceHotel']").value;
        searchHotelByLocationAndPrice(currentNameHotel, currentLocation, currentPriceHotel, currentPage, itemsPerPage);
        const searchUrl = new URL(window.location);
        if (currentNameHotel) searchUrl.searchParams.set('nameHotel', currentNameHotel);
        if (currentLocation) searchUrl.searchParams.set('location', currentLocation);
        if (currentPriceHotel) searchUrl.searchParams.set('priceHotel', currentPriceHotel);
        history.pushState(null, '', searchUrl);
    });

    // Xử lý phân trang
    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            // giu bo loc khi chuyen trang
            if (currentNameHotel || currentLocation || currentPriceHotel) {
                searchHotelByLocationAndPrice(currentNameHotel, currentLocation, currentPriceHotel, currentPage, itemsPerPage);
            } else {
                fetchHotelUser(currentPage, itemsPerPage);
            }
        }
    });

    document.getElementById("nextPage").addEventListener("click", function () {
        currentPage++;
        if (currentNameHotel || currentLocation || currentPriceHotel) {
            searchHotelByLocationAndPrice(currentNameHotel, currentLocation, currentPriceHotel, currentPage, itemsPerPage);
        } else {
            fetchHotelUser(currentPage, itemsPerPage);
        }
    });
});

async function fetchHotelUser(page, limit) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/hotel/allForUser?page=${page}&limit=${limit}`);
        if (!response.ok) {
            console.error("Không lấy được thông tin phòng. Hãy xem lại trong hotelUser.js");
            return;
        }
        const hotelData = await response.json();
        displayHotelForUser(hotelData.content);
        updatePaginationHotel(hotelData);
    } catch (error) {
        console.error("error: ", error);
    }
}

async function searchHotelByLocationAndPrice(nameHotel, location, priceHotel, page, limit) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/hotel/searchForUser?nameHotel=${encodeURIComponent(nameHotel)}&location=${encodeURIComponent(location)}&priceHotel=${encodeURIComponent(priceHotel)}&page=${page}&limit=${limit}`);
        if (!response.ok) {
            console.error("Không tìm thấy khách sạn.");
            return;
        }
        const hotels = await response.json();
        console.log(hotels.content)
        if(hotels.length === 0){
            document.getElementById("hotelData").innerHTML = "Không tìm thấy khách sajn phù hợp";
        }
        displayHotelForUser(hotels.content);
        updatePaginationHotel(hotels);
    } catch (error) {
        console.error("Lỗi khi tìm kiếm khách sạn:", error);
    }
}

function displayHotelForUser(hotel) {
    var hotelData = document.getElementById("hotelData");
    hotelData.innerHTML = "";
    hotel.forEach(hotel => {
        let formattedPrice = hotel.price ? hotel.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(hotel.imageHotel);
        let timestamp = new Date().getTime();
        let card = `
            <div class="card">
                <div class="badge">⭐ 5 </div>
                <img class="card-img-top" src="/uploadsHotel/${imageName}?t=${timestamp}" alt="${hotel.name}">
                <div class="card-body">
                    <div class="location">📍 ${hotel.location}</div>
                    <h5 class="nameHotel">${hotel.name}</h5>
                    <p>${hotel.availableRooms} phòng</p>
                    <p style="font-weight: 700">Giá mỗi đêm: </p>
                    <div class="price">${formattedPrice}đ / phòng</div>
                    <button class="btn-custom" onclick="viewRoom(${hotel.id})">Đặt ngay</button>
                </div>
            </div>
        `;
        hotelData.innerHTML += card;
    });
}

function viewRoom(hotelId) {
    console.log(hotelId);
    localStorage.setItem("hotelId", JSON.stringify(hotelId));
    window.location.href = "/user/room";
}

function updatePaginationHotel(data) {
    const prevButton = document.getElementById("prevPage");
    const nextButton = document.getElementById("nextPage");

    prevButton.disabled = data.pageable.pageNumber === 0;
    nextButton.disabled = data.pageable.pageNumber >= data.totalPages - 1;

    document.getElementById("pageInfo").textContent = `Trang ${data.pageable.pageNumber + 1} / ${data.totalPages}`;
}
