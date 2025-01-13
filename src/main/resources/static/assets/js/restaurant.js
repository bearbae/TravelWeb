
document.addEventListener("DOMContentLoaded", function (){
    fetchRestaurant() ;

    const searchForm = document.getElementById("form")
    searchForm.addEventListener("submit", function (event) {
        event.preventDefault();
        const nameRestaurantInput = document.querySelector("input[name='nameRestaurant']").value;
        const locationInput = document.querySelector("input[name='location']").value;
        const priceRestaurantInput = document.querySelector("select[name='priceRestaurant']").value;
        searchRestaurantByLocationAndPrice(nameRestaurantInput,locationInput, priceRestaurantInput);
        const searchUrl = new URL(window.location);
        if(nameRestaurantInput) searchUrl.searchParams.set('nameRestaurant', nameRestaurantInput);
        if(locationInput) searchUrl.searchParams.set('location', locationInput);
        if(priceRestaurantInput) searchUrl.searchParams.set('priceRestaurant', priceRestaurantInput);
        history.pushState(null, '', searchUrl);
    });
})
async function fetchRestaurant() {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/restaurant/all`);
        if (!response.ok) {
            console.error("Không lấy được thông tin bàn. Hãy xem lại trong restaurantUser.js");
            return;
        }
        const restaurant = await response.json();
        console.log(restaurant);
        displayRestaurant(restaurant);
    } catch (error) {
        console.error("error: ", error);
    }
}


async function searchRestaurantByLocationAndPrice(nameRestaurant,location, priceRestaurant) {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/restaurant/search?nameRestaurant=${encodeURIComponent(nameRestaurant)}&location=${encodeURIComponent(location)}&priceRestaurant=${encodeURIComponent(priceRestaurant)}`);
        if (!response.ok) {
            console.error("Không tìm thấy nhà hàng.");
            return;
        }
        const restaurants = await response.json();
        displayRestaurant(restaurants);
    } catch (error) {
        console.error("Lỗi khi tìm kiếm nhà hàng:", error);
    }
}
function displayRestaurant(restaurant) {
    var restaurantData = document.getElementById("restaurantData");
    restaurantData.innerHTML = "";
    restaurant.forEach( restaurant =>{
        let formattedPrice = restaurant.price ? restaurant.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(restaurant.imageRestaurant);
        let timestamp = new Date().getTime();
        let row =  `
                    <tr>
                        <td>${restaurant.id}</td>
                        <td>${restaurant.name}</td>
                        <td>${restaurant.location}</td>
                        <td>${formattedPrice}</td>
                        <td>${restaurant.availableTables}</td>
                        <td style="width: 200px;"><img src="/uploadsRestaurant/${imageName}?t=${timestamp}" alt="${restaurant.name}" style="max-width: 100%;height: auto; object-fit: cover;"  /></td>
                        <td style="width: 350px;
                            overflow: hidden;
                            text-overflow: ellipsis;
                            display: -webkit-box;
                            -webkit-box-orient: vertical;
                            -webkit-box-align: start;
                            -webkit-line-clamp: 2">${restaurant.description}</td>
                        <td>
                            <button class="btn btn-view" onclick="viewTable(${restaurant.id})">Xem Bàn</button>
                        </td>
                        <td style="width: 130px;">
                            <button class="btn btn-success" onclick="editRestaurant(${restaurant.id})">Sửa</button>
                            <button class="btn btn-danger" onclick="deleteRestaurant(${restaurant.id})">Xóa</button>
                        </td>
                    </tr>
            ` ;
        restaurantData.innerHTML += row ;
    })
}

function editRestaurant(restaurantId){
    fetch(`http://localhost:8080/api/admin/restaurant/${restaurantId}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin nhà hàng!";
            }
            return response.json();
        })
        .then(data => {
             localStorage.setItem("restaurant", JSON.stringify(data));
             window.location.href = "/admin/restaurant/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteRestaurant(restaurantId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/restaurant/delete/${restaurantId}`, {
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

 function viewTable(restaurantId) {
     console.log(restaurantId);
     localStorage.setItem("restaurantId", JSON.stringify(restaurantId));
     window.location.href = "/admin/restaurant/table";
}
