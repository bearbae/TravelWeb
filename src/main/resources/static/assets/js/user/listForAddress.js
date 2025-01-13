document.addEventListener("DOMContentLoaded", async function (){
    getListTour() ;
    getListHotel();
    getListRestaurant();
});


async function getListTour() {
    const nameAddress = JSON.parse(localStorage.getItem("nameAddress"))  ;
    const response = await  fetch(`http://localhost:8080/api/admin/tour/getTourByAddress/${nameAddress}`)
    const allTourData = await response.json() ;
    var card_allTour = document.getElementById("card-allTour") ;
    card_allTour.innerHTML = `
        <h2 class="section-title">Top địa điểm du lịch tại ${nameAddress}</h2>
    `;
    allTourData.forEach(tour => {
        let formattedPrice = tour.price ? tour.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(tour.imageOfTour.img1);
        let timestamp = new Date().getTime();
        let card = `
                <div class="col-md-3" onclick="viewService(${tour.id})" style="margin-top: 15px">
                    <div class="card">
                        <img src="/uploadsTour/${imageName}?t=${timestamp}" class="card-img-top" alt="${tour.typeTour}">
                        <div class="card-body">
                            <div class="card-subtext">${tour.typeTour} • ${tour.address}</div>
                            <div class="card-title">${tour.titleTour}</div>
                            <div class="general-tag">
                                <div class="tag">Bán chạy</div>
                                <div class="tag">Miễn phí hủy</div>
                            </div>
                            <div class="rating">
                                <div class="star">★ ${tour.rateStar}</div>
                                <div class="quantity_comment" style="margin-right: 5px">(${tour.quantityComment})</div>
                                <div class="booked">
                                    • ${tour.booked}+ Đã được đặt
                                </div>
                            </div>
                            <div class="card-price">Giá từ ${formattedPrice} đ</div>
                        </div>
                    </div>
                </div>
            `;
        card_allTour.innerHTML += card;
    });
}

async function getListHotel() {
    const address = JSON.parse(localStorage.getItem("nameAddress"))  ;
    const response = await  fetch(`http://localhost:8080/api/admin/hotel/getByLocation/${address}`)
    const listHotelData = await response.json() ;
    var listHotell = document.getElementById("listHotel") ;
    listHotell.innerHTML = `
        <h2 class="section-title" style="margin-top: 30px">Những khách sạn tại ${address} </h2>
`;
    console.log(listHotelData);
    listHotelData.forEach(hotel => {
        let formattedPrice = hotel.price ? hotel.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(hotel.imageHotel);
        let timestamp = new Date().getTime();
        let card = `
            <div class="col-md-3" onclick="viewHotel(${hotel.id})" style="margin-top: 15px">
                <div class="card">
                    <img class="card-img-top" src="/uploadsHotel/${imageName}?t=${timestamp}" alt="${hotel.name}">
                    <div class="card-body">
                        <div class="location">📍 ${hotel.location}</div>
                        <h5 class="nameHotel">${hotel.name}</h5>
                        <p>${hotel.availableRooms} phòng</p>
                        <p style="font-weight: 700">Giá mỗi đêm: </p>
                        <div class="card-price">${formattedPrice}đ / phòng</div>
                    </div>
                </div>   
            </div>
        `;
        listHotell.innerHTML += card;
    });

}

async function getListRestaurant() {
    const address = JSON.parse(localStorage.getItem("nameAddress"))  ;
    const response = await  fetch(`http://localhost:8080/api/admin/restaurant/getByLocation/${address}`)
    const listRestaurantData = await response.json() ;
    var listRes = document.getElementById("listRestaurant") ;
    listRes.innerHTML = `
        <h2 class="section-title" style="width: 100%; color: #000; margin-top: 30px">Những nhà hàng tại ${address} </h2>
`;
    console.log(listRestaurantData);
    listRestaurantData.forEach(restaurant => {
        let formattedPrice = restaurant.price ? restaurant.price.toLocaleString('vi-VN') : 'N/A';
        let imageName = encodeURIComponent(restaurant.imageRestaurant);
        let timestamp = new Date().getTime();
        let card = `
            <div class="col-md-3" onclick="viewRestaurant(${restaurant.id})" style="margin-top: 15px; ">
                <div class="card">
                    <img class="card-img-top" src="/uploadsRestaurant/${imageName}?t=${timestamp}" alt="${restaurant.name}">
                    <div class="card-body">
                        <div class="location">📍 ${restaurant.location}</div>
                        <h5 class="nameRestaurant">${restaurant.name}</h5>
                        <p>${restaurant.availableTables} bàn</p>
                        <p style="font-weight: 700">Giá : </p>
                        <div class="card-price">${formattedPrice}đ/người</div>
                    </div>
                </div>   
            </div>
        `;
        listRes.innerHTML += card;
    });

}

 function viewService(idTour){
    localStorage.setItem("idTour", JSON.stringify(idTour));
    window.location.href = "/user/servicePackage";
}
function viewHotel(hotelId) {
    localStorage.setItem("hotelId", JSON.stringify(hotelId));
    window.location.href = "/user/room";
}

function viewRestaurant(restaurantId) {
    localStorage.setItem("restaurantId", JSON.stringify(restaurantId));
    window.location.href = "/user/table";
}