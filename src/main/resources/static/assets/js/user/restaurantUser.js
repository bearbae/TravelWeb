document.addEventListener("DOMContentLoaded", function () {
    let currentPage = 0;
    const itemsPerPage = 9;
    let currentNameRestaurant = "";
    let currentLocation = "";
    let currentPriceRestaurant = "allPrice";

    fetchRestaurantUser(currentPage, itemsPerPage);

    const searchForm = document.getElementById("form");
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault(); // Ngăn chặn reload trang khi submit
         currentNameRestaurant = document.querySelector("input[name='nameRestaurant']").value;
         currentLocation = document.querySelector("input[name='location']").value;
         currentPriceRestaurant = document.querySelector("select[name='priceRestaurant']").value;
        searchRestaurantByLocationAndPrice(currentNameRestaurant, currentLocation, currentPriceRestaurant, currentPage, itemsPerPage);
        const searchUrl = new URL(window.location);
        if (currentNameRestaurant) searchUrl.searchParams.set('nameRestaurant', currentNameRestaurant);
        if (currentLocation) searchUrl.searchParams.set('location', currentLocation);
        if (currentPriceRestaurant) searchUrl.searchParams.set('priceRestaurant', currentPriceRestaurant);
        history.pushState(null, '', searchUrl);
    });

    // Xử lý phân trang
    document.getElementById("prevPage").addEventListener("click", function () {
        if (currentPage > 0) {
            currentPage--;
            // giu bo loc khi chuyen trang
            if (currentNameRestaurant || currentLocation || currentPriceRestaurant) {
                searchRestaurantByLocationAndPrice(currentNameRestaurant, currentLocation, currentPriceRestaurant, currentPage, itemsPerPage);
            } else {
                fetchRestaurantUser(currentPage, itemsPerPage);
            }
        }
    });

    document.getElementById("nextPage").addEventListener("click", function () {
        currentPage++;
        if (currentNameRestaurant || currentLocation || currentPriceRestaurant) {
            searchRestaurantByLocationAndPrice(currentNameRestaurant, currentLocation, currentPriceRestaurant, currentPage, itemsPerPage);
        } else {
            fetchRestaurantUser(currentPage, itemsPerPage);
        }
    });
});

async function fetchRestaurantUser(page, limit) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/restaurant/allForUser?page=${page}&limit=${limit}`);
        if (!response.ok) {
            console.error("Không lấy được thông tin bàn. Hãy xem lại trong restaurantUser.js");
            return;
        }
        const restaurantData = await response.json();
        displayRestaurantForUser(restaurantData.content);
        updatePaginationRestaurant(restaurantData);
    } catch (error) {
        console.error("error: ", error);
    }
}

async function searchRestaurantByLocationAndPrice(nameRestaurant, location, priceRestaurant, page, limit) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/restaurant/searchForUser?nameRestaurant=${encodeURIComponent(nameRestaurant)}&location=${encodeURIComponent(location)}&priceRestaurant=${encodeURIComponent(priceRestaurant)}&page=${page}&limit=${limit}`);
        if (!response.ok) {
            console.error("Không tìm thấy nhà hàng.");
            return;
        }
        const restaurants = await response.json();
        console.log(restaurants.content)
        displayRestaurantForUser(restaurants.content);
        updatePaginationRestaurant(restaurants);
    } catch (error) {
        console.error("Lỗi khi tìm kiếm nhà hàng:", error);
    }
}

function displayRestaurantForUser(restaurant) {
    var restaurantData = document.getElementById("restaurantData");
    restaurantData.innerHTML = "";
    restaurant.forEach(restaurant => {
        let formattedPrice = restaurant.price ? restaurant.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(restaurant.imageRestaurant);
        let timestamp = new Date().getTime();
        let card = `
            <div class="card">
                <div class="badge">⭐ 4 </div>
                <img class="card-img-top" src="/uploadsRestaurant/${imageName}?t=${timestamp}" alt="${restaurant.name}">
                <div class="card-body">
                    <div class="location">📍 ${restaurant.location}</div>
                    <h5 class="nameRestaurant">${restaurant.name}</h5>
                    <p>${restaurant.availableTables} bàn</p>
                    <p style="font-weight: 700">Giá chỉ từ: </p>
                    <div class="price">${formattedPrice}đ / người</div>
                    <button class="btn-custom" onclick="viewTable(${restaurant.id})">Đặt ngay</button>
                </div>
            </div>
        `;
        restaurantData.innerHTML += card;
    });
}

function viewTable(restaurantId) {
    console.log(restaurantId);
    localStorage.setItem("restaurantId", JSON.stringify(restaurantId));
    window.location.href = "/user/table";
}

function updatePaginationRestaurant(data) {
    const prevButton = document.getElementById("prevPage");
    const nextButton = document.getElementById("nextPage");

    prevButton.disabled = data.pageable.pageNumber === 0;
    nextButton.disabled = data.pageable.pageNumber >= data.totalPages - 1;

    document.getElementById("pageInfo").textContent = `Trang ${data.pageable.pageNumber + 1} / ${data.totalPages}`;
}
