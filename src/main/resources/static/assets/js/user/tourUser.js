document.addEventListener("DOMContentLoaded", function () {
    const prevBtn = document.getElementById('btn-prev');
    const nextBtn = document.getElementById('btn-next');
    const cardContainer = document.getElementById('card-container');

    let currentIndex = 0;
    let totalCards = 0;
    const cardsPerView = 4;
    let cardsData = [];

    async function fetchCardTours() {
        try {
            const response = await fetch('http://localhost:8080/api/admin/tour/getAll');
            const data = await response.json();
            cardsData = data;
            totalCards = data.length;
            displayCards();
        } catch (error) {
            console.error('Lỗi khi gọi API:', error);
        }
    }
    // xu ly view all tour
    document.getElementById("viewAllTour").addEventListener("click", async function (){
        const response = await fetch('http://localhost:8080/api/admin/tour/getAll');
        const data = await response.json();
        localStorage.setItem("AllTour", JSON.stringify(data)) ;
        window.location.href = "/user/allTour" ;
    })
    function displayCards() {
        cardContainer.innerHTML = "";
        const start = currentIndex;
        const end = Math.min(start + cardsPerView, totalCards);

        // Lặp qua dữ liệu trong khoảng [start, end)
        for (let i = start; i < end; i++) {
            const item = cardsData[i];
            let formattedPrice = item.price ? item.price.toLocaleString('vi-VN') : 'N/A';
            let imageName = encodeURIComponent(item.imageOfTour.img1);
            let timestamp = new Date().getTime();
            let card = `
                <div class="col-md-3" onclick="viewService(${item.id})">
                    <div class="card">
                        <img src="/uploadsTour/${imageName}?t=${timestamp}" class="card-img-top" alt="${item.typeTour}">
                        <div class="card-body">
                            <div class="card-subtext">${item.typeTour} • ${item.address}</div>
                            <div class="card-title">${item.titleTour}</div>
                            <div class="general-tag">
                                <div class="tag">Bán chạy</div>
                                <div class="tag">Miễn phí hủy</div>
                            </div>
                            <div class="rating">
                                <div class="star">★ ${item.rateStar}</div>
                                <div class="quantity_comment" style="margin-right: 5px">(${item.quantityComment})</div>
                                <div class="booked">
                                    • ${item.booked}k+ Đã được đặt
                                </div>
                            </div>
                            <div class="card-price">Giá từ ${formattedPrice} đ</div>
                        </div>
                    </div>
                </div>
            `;
            cardContainer.innerHTML += card;
        }
        updateButtons();
    }

    function updateButtons() {
        prevBtn.disabled = currentIndex === 0;
        nextBtn.disabled = currentIndex + cardsPerView >= totalCards;
    }

    nextBtn.addEventListener('click', () => {
        if (currentIndex + cardsPerView < totalCards) {
            currentIndex += cardsPerView;
            displayCards();
        }
    });

    // Sự kiện nút "Prev"
    prevBtn.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex -= cardsPerView;
            displayCards();
        }
    });
    fetchCardTours();
    getAddress();
});

function getAddress() {
    const prevBtn = document.getElementById('btn-prev2');
    const nextBtn = document.getElementById('btn-next2');
    const dataDestination = document.getElementById('dataDestination');

    let currentIndex = 0;
    let totalCards = 0;
    const cardsPerView = 6;
    let cardsData = [];

    async function fetchCardAddresss() {
        try {
            const response = await fetch('http://localhost:8080/api/admin/address/getAll');
            const data = await response.json();
            cardsData = data;
            totalCards = data.length;
            displayCardDestinations();
        } catch (error) {
            console.error('Lỗi khi gọi API:', error);
        }
    }
    function displayCardDestinations() {
        dataDestination.innerHTML = "";
        const start = currentIndex;
        const end = Math.min(start + cardsPerView, totalCards);

        // Lặp qua dữ liệu trong khoảng [start, end)
        for (let i = start; i < end; i++) {
            const item = cardsData[i];
            let imageName = encodeURIComponent(item.img);
            let timestamp = new Date().getTime();
            let card = `
                <div class="destination-card" onclick="viewAddress('${item.nameAddress}')">
                    <img src="/uploadsAddress/${imageName}?${timestamp}" alt="${item.nameAddress}">
                    <div class="destination-title">${item.nameAddress}</div>
                </div>
            `;
            dataDestination.innerHTML += card;
        }
        updateButtons();
    }

    function updateButtons() {
        prevBtn.disabled = currentIndex === 0;
        nextBtn.disabled = currentIndex + cardsPerView >= totalCards;
    }

    nextBtn.addEventListener('click', () => {
        if (currentIndex + cardsPerView < totalCards) {
            currentIndex += cardsPerView;
            displayCardDestinations();
        }
    });

    // Sự kiện nút "Prev"
    prevBtn.addEventListener('click', () => {
        if (currentIndex > 0) {
            currentIndex -= cardsPerView;
            displayCardDestinations();
        }
    });
    fetchCardAddresss();
}
async function viewService(idTour){
    localStorage.setItem("idTour", JSON.stringify(idTour));
    window.location.href = "/user/servicePackage";
}
async function viewAddress(nameAddress){
    localStorage.setItem("nameAddress", JSON.stringify(nameAddress));
    window.location.href = "/user/listForAddress";
}