document.addEventListener("DOMContentLoaded", function (){
    const allTourData = JSON.parse(localStorage.getItem("AllTour"))  ;
    var card_allTour = document.getElementById("card-allTour") ;
    card_allTour.innerHTML = "";
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
});

async function viewService(idTour){
    localStorage.setItem("idTour", JSON.stringify(idTour));
    window.location.href = "/user/servicePackage";
}