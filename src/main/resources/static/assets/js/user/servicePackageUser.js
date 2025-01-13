document.addEventListener("DOMContentLoaded", function (){
    fetchDataTour();
    BookingTour();
    handleModal();
});
let anh1 = "" ;
let anh2 = "" ;
let anh3 = "" ;
let anh4 = "" ;
let anh5 = "" ;
async function fetchDataTour(){
    var dataTour = document.getElementById("dataTour");
    var contentTour = document.getElementById("contentTour");
    contentTour.innerHTML = "";
    dataTour.innerHTML = "";
    var idTour = JSON.parse(localStorage.getItem("idTour"));
    const response = await fetch(`http://localhost:8080/api/admin/tour/getTour/${idTour}`);
    const data = await response.json();
    localStorage.setItem("tourData", JSON.stringify(data));
    const typeTour = data.typeTour;
    displayService(typeTour,idTour);
    console.log(data)
    let imageName1 = anh1 = encodeURIComponent(data.imageOfTour.img1);
    let imageName2 = anh2 = encodeURIComponent(data.imageOfTour.img2);
    let imageName3 = anh3 = encodeURIComponent(data.imageOfTour.img3);
    let imageName4 = anh4 = encodeURIComponent(data.imageOfTour.img4);
    let imageName5 = anh5 = encodeURIComponent(data.imageOfTour.img5);
    let timestamp = new Date().getTime();
    let formattedPrice = data.price ? data.price.toLocaleString('vi-VN') : 'N/A';
    dataTour.innerHTML = `
            <h1 class="fw-bold">${data.titleTour}</h1>
            <div class="d-flex align-items-center mt-3">
              <span class="badge bg-warning text-dark">Bán chạy</span>
              <span class="ms-3">🌐 Tiếng Hoa/Tiếng Anh/Tiếng Nhật/Tiếng Hàn</span>
            </div>
            <div class="mt-3">
              <span class="text-warning fs-5">★ ${data.rateStar}</span>
              <span>(${data.quantityComment}K+ Đánh giá)</span>
              <span class="ms-3">${data.booked}K+ Đã đặt</span>
            </div>
            
            <div class="row gallery " style="margin-bottom: 10px">
              <div class="col-md-8 mb-3">
                <img src="/uploadsTour/${imageName1}?t=${timestamp}" class="img-fluid rounded h-100" alt="anh 1">
              </div>
              <div class="col-md-4 mb-3">
                <div class="row h-100">
                  <div class="col-12 mb-3">
                    <img src="/uploadsTour/${imageName2}?t=${timestamp}" class="img-fluid rounded h-100" alt="anh 2">
                  </div>
                  <div class="col-12">
                    <img src="/uploadsTour/${imageName3}?t=${timestamp}" class="img-fluid rounded h-100" alt="anh 3">
                  </div>
                </div>
              </div>
            </div>
           
            <div class="row">
              <div class="col-md-8">
                <div class="p-3 bg-light rounded">
                  <ul class="mb-0">
                    <li>${data.highlight}</li>
                  </ul>
                  <a href="#" class="text-primary d-block mt-2">Xem thêm &gt;</a>
                </div>
              </div>
              <div class="col-md-4 d-flex align-items-center">
                <div class="text-center w-100 border rounded p-3">
                  <h3 class="text-danger mb-3">${formattedPrice} đ</h3>
                  <button class="btn btn-warning text-white fw-bold px-4">Chọn các gói dịch vụ</button>
                </div>
              </div>
            </div>
    `;
    contentTour.innerHTML = `
              <h4 class="fw-bold text-orange">Về dịch vụ này</h4>
              <div class="row">
                <div class="col-md-8">
                  <div class="rounded">
                    <p class="content">${data.content}</p>
                  </div>
            
                </div>
                <div class="col-md-8 mb-3">
                  <img src="/uploadsTour/${imageName3}?t=${timestamp}" class="img-fluid rounded img_service" alt="anh 3">
                  <img src="/uploadsTour/${imageName4}?t=${timestamp}" class="img-fluid rounded img_service" alt="anh 4">
                  <img src="/uploadsTour/${imageName5}?t=${timestamp}" class="img-fluid rounded img_service" alt="anh 5">
                </div>
              </div>
    ` ;

}

// hien thi dịch vu
 function displayService(typeTour,idTour){
    var typeIsTour = document.getElementById("typeIsTour");
    var typeIsYacht  = document.getElementById("typeIsYacht");
    var typeIsTicket = document.getElementById("typeIsTicket");
    if(typeTour == "Tour"){
        typeIsTour.style.display = "block";
        typeIsYacht.style.display = "none";
        typeIsTicket.style.display = "none" ;
    } else if(typeTour == "Du thuyền") {
        typeIsYacht.style.display = "block";
        typeIsTour.style.display = "none";
        typeIsTicket.style.display = "none" ;
    } else {
        typeIsTicket.style.display = "block";
        typeIsTour.style.display = "none";
        typeIsYacht.style.display = "none" ;
    }
    // var container = typeTour === "Tour" ? typeIsTour : typeIsYacht;
     var container;
     if (typeTour === "Tour") {
         container = typeIsTour;
     } else if (typeTour === "Du thuyền") {
         container = typeIsYacht;
     } else if (typeTour === "Vé tham quan") {
         container = typeIsTicket;
     } else {
         container = null;
     }
    container.addEventListener('click', async function(event) {
        var target = event.target;
        if (target.tagName === 'BUTTON' && target.hasAttribute('name')) {
            document.querySelector(".quantity-adult").value = 0;
            document.querySelector(".quantity-child").value = 0;
            var code = target.getAttribute('name');
            var detailService = document.getElementById('detailService');
            const detailContainer = document.querySelector('.detail-container');
            detailService.innerHTML = "";
            console.log(code);
            const response = await fetch(`http://localhost:8080/api/serviceTour/${code}/${idTour}`);
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const dataDetail = await response.json();
            console.log(dataDetail);
            let formattedPriceAdult = dataDetail.pricePerAdult ? dataDetail.pricePerAdult.toLocaleString('vi-VN') : 'N/A';
            let formattedPriceChild = dataDetail.pricePerChild ? dataDetail.pricePerChild.toLocaleString('vi-VN') : 'N/A';
            document.querySelector(".pricePerAdult").textContent = `${formattedPriceAdult} đ`;
            document.querySelector(".pricePerChild").textContent = `${formattedPriceChild} đ`;
            document.querySelector(".totalPrice").textContent = `0 đ`;
            totalPrice = 0 ;
            const times = [
                dataDetail.time1,
                dataDetail.time2,
                dataDetail.time3,
                dataDetail.time4,
                dataDetail.time5,
                dataDetail.timeToGo,
                dataDetail.timeToBack
            ];
            const formattedTime = times.map( time =>
                new Date(`1970-01-01T${time}`).toLocaleTimeString("en-US", {
                    hour: "2-digit",
                    minute: "2-digit",
                    hour12: true
            })
            );
            if(code == "2day"){
                detailContainer.style.display = 'block';
                detailService.innerHTML = `
                      <div class="mt-4">
                        <h6 class="fw-bold">Lịch trình</h6>
                        <div>
                          <p><strong>Ngày 1</strong>
                          <p><strong>${dataDetail.timeToGo}</strong> • Khởi hành</p>
                          <p><i class="bi bi-bus-front-fill"></i>• Phương tiện: ${dataDetail.vehicle}</p>
                          <p><strong>${formattedTime[0]}</strong> • Điểm đến 1: ${dataDetail.address1}</p>
                          <img src="/uploadsTour/${anh1}" alt="img" class="img-fluid mb-3">
                          <p>Tour có hướng dẫn viên.</p>
                          <p><strong>${formattedTime[1]}</strong> • Điểm đến 2: ${dataDetail.address2}</strong></p>
                          <p>Tour có hướng dẫn viên.</p>
                          <p><strong>Bữa trưa</strong></p>
                          <p>Tận hưởng bữa trưa đa dạng món với sự phục vụ của nhà hàng.</p>
                          <img src="/uploadsTour/${anh2}" alt="img" class="img-fluid mb-3">
                          <p><strong>${formattedTime[6]}</strong> • Quay về</p>
                          <p><strong>Ngày 2</strong>
                          <p><strong>${formattedTime[5]}</strong> • Khởi hành</p>
                          <p><strong>${formattedTime[2]}</strong>• Điểm đến 3: ${dataDetail.address3}</p>
                          <p>Tour có hướng dẫn viên. </p>
                          <p><strong>Bữa trưa</strong></p>
                          <p><strong>${formattedTime[6]}</strong> • Quay về</p>
                          <p>* Lịch trình có thể thay đổi tùy vào điều kiện giao thông và thời tiết</p>
                        </div>
                      </div>
                `;

            } else if(code == "1day") {
                detailContainer.style.display = 'block';
                detailService.innerHTML = `
                      <div class="mt-4">
                        <h6 class="fw-bold">Lịch trình</h6>
                        <div>
                          <p><strong>${formattedTime[5]}</strong> • Khởi hành</p>
                          <p><i class="bi bi-bus-front-fill"></i> • Phương tiện: ${dataDetail.vehicle}</p>
                          <p><strong>${formattedTime[0]}</strong> • Điểm đến 1: ${dataDetail.address1}</p>
                           <img src="/uploadsTour/${anh4}" alt="img" class="img-fluid mb-3">
                          <p>Tour có hướng dẫn viên.</p>
                          <p><strong>${formattedTime[1]}</strong> • Điểm đến 2: ${dataDetail.address2}</p>
                          <p>Tour có hướng dẫn viên.</p>
                          <p><strong>Bữa trưa</strong></p>
                          <p>Have lunch local restaurant.</p>
                          <p><strong>${formattedTime[1]}</strong> • Điểm đến 3: ${dataDetail.address3}</p>
                          <p>Tour có hướng dẫn viên.</p>
                           <img src="/uploadsTour/${anh5}" alt="img" class="img-fluid mb-3">
                          <p><strong>${formattedTime[6]}</strong> • Quay về</p>
                          <p>* Lịch trình có thể thay đổi tùy vào điều kiện giao thông và thời tiết</p>
                          `;
            } else if(code == "5 món đặc biệt" || code == "5 món" || code == "4 món chay" || code == "4 món" || code == "3 món"){
                detailContainer.style.display = 'block';
                detailService.innerHTML = `
                      <div class="mt-4">
                        <h6 class="fw-bold">Lịch trình</h6>
                        <div>
                          <p><strong>${formattedTime[5]}</strong> • Khởi hành</p>
                          <p><strong>${formattedTime[0]}</strong> • Điểm đến : ${dataDetail.address1}</p>
                           <img src="/uploadsTour/${anh4}" alt="img" class="img-fluid mb-3">
                          <p><strong>Bữa tối</strong></p>
                           <img src="/uploadsTour/${anh5}" alt="img" class="img-fluid mb-3">
                          <p><strong>${formattedTime[6]}</strong> • Quay về</p>
                          <strong>Bao gồm</strong>
                          <ul>
                            <li>Bảo hiểm do nhà điều hành cung cấp</li>
                            <li>Du thuyền 2 giờ</li>
                            <li>Nhạc live hoặc show biểu diễn ảo thuật</li>
                            <li>Bữa tối ${dataDetail.code}</li>
                            <li>Chi phí cá nhân khác</li>
                            <li>Tiền típ</li>
                          </ul>
                          <p>* Lịch trình có thể thay đổi tùy vào điều kiện giao thông và thời tiết</p>
                          `;
            } else if(code == "cbtk" || code == "cbgđ" || code == "cbtn"){
                detailContainer.style.display = 'block';
                detailService.innerHTML = `
                      <div class="mt-4">
                        <h6 class="fw-bold">Bao gồm</h6>
                        <div>
                          <p>Vé vào cổng tham quan</p>
                           <img src="/uploadsTour/${anh3}" alt="img" class="img-fluid mb-3">
                          <strong>Điều kiện</strong>
                          <ul>
                            <li>Miễn phí cho trẻ em cao từ 99cm trở xuống</li>
                          </ul>
                          `;
            }
            addQuantityControls();
        }
    });
}
// hiển thị detail
let selectContentButton = "" ;
const selectedButtons = {
    tourDuration : null,
    typeTour: null,
    hdv: null,
};
function showDetails(button) {
    const buttonContent = button.textContent;

    const container = button.closest('.tour-group');
    const groupKey = container.getAttribute('data-group');

    const buttons = container.querySelectorAll('.tour-button');

    buttons.forEach(button => {
        button.classList.remove('btn-outline-danger');
        button.classList.add('btn-outline-secondary');
    });
    button.classList.add('btn-outline-danger');
    button.classList.remove('btn-outline-secondary');
    selectContentButton = buttonContent ;

    selectedButtons[groupKey] = buttonContent;

    console.log(`Selected values:`, selectedButtons);
}

function addQuantityControls() {
    const decreaseAdultBtn = document.querySelector(".decrease-adult");
    const increaseAdultBtn = document.querySelector(".increase-adult");
    const decreaseChildBtn = document.querySelector(".decrease-child");
    const increaseChildBtn = document.querySelector(".increase-child");
    const quantityAdult = document.querySelector(".quantity-adult");
    const quantityChild = document.querySelector(".quantity-child");
    const priceAdult = document.querySelector(".pricePerAdult");
    const priceChild = document.querySelector(".pricePerChild");
    const pricePerAdult = parseInt(priceAdult.textContent.replace(/[^0-9]/g, ''));
    const pricePerChild = parseInt(priceChild.textContent.replace(/[^0-9]/g, ''));
// sao chep
    const newDecreaseAdultBtn = decreaseAdultBtn.cloneNode(true);
    const newIncreaseAdultBtn = increaseAdultBtn.cloneNode(true);
    const newDecreaseChildBtn = decreaseChildBtn.cloneNode(true);
    const newIncreaseChildBtn = increaseChildBtn.cloneNode(true);

    decreaseAdultBtn.parentNode.replaceChild(newDecreaseAdultBtn, decreaseAdultBtn);
    increaseAdultBtn.parentNode.replaceChild(newIncreaseAdultBtn, increaseAdultBtn);
    decreaseChildBtn.parentNode.replaceChild(newDecreaseChildBtn, decreaseChildBtn);
    increaseChildBtn.parentNode.replaceChild(newIncreaseChildBtn, increaseChildBtn);
    newDecreaseAdultBtn.addEventListener("click", function () {
        let currentQuantity = parseInt(quantityAdult.value);
        if (currentQuantity > 0) {
            currentQuantity -= 1;
            quantityAdult.value = currentQuantity;
            updateTotalPrice(-pricePerAdult);
            console.log(pricePerAdult);
        }
    });
    newIncreaseAdultBtn.addEventListener("click", function () {

        priceAdult.style.display = "block";
        let currentQuantity = parseInt(quantityAdult.value);
            currentQuantity += 1;
            quantityAdult.value = currentQuantity;
            updateTotalPrice(pricePerAdult);
        console.log(pricePerAdult);

    });
    newDecreaseChildBtn.addEventListener("click", function () {
        let currentQuantity = parseInt(quantityChild.value);
        if (currentQuantity > 0) {
            currentQuantity -= 1;
            quantityChild.value = currentQuantity;
            updateTotalPrice(-pricePerChild);
            console.log(pricePerChild);

        }
    });
    newIncreaseChildBtn.addEventListener("click", function () {
        priceChild.style.display = "block";
        let currentQuantity = parseInt(quantityChild.value);
        currentQuantity += 1;
        quantityChild.value = currentQuantity;
        updateTotalPrice(pricePerChild);
        console.log(pricePerChild);

    });
}
let totalPrice = 0;

// cap nhat gia tien
function updateTotalPrice(priceChange){
    totalPrice += priceChange ;
    document.querySelector(".totalPrice").textContent = `${totalPrice.toLocaleString('vi-VN')} đ`
}

// tao dajt tour
function BookingTour() {
    const createBookTour = document.getElementById("ConfirmBookTour");
    createBookTour.addEventListener("click", async function () {
        var user = JSON.parse(localStorage.getItem("user"));
        var tour = JSON.parse(localStorage.getItem("tourData"));
        const dateBook = document.getElementById("datePickerButton").value;
        const tourDuration = selectedButtons.tourDuration ;
        const typeService = selectedButtons.typeTour;
        const hdv = selectedButtons.hdv;
        const quantityAdults = document.querySelector(".quantity-adult").value;
        const quantityChildren = document.querySelector(".quantity-child").value;
        const totalAmount = parseInt(document.querySelector(".totalPrice").textContent.replace(/[^0-9]/g, ''));
        const fullName = document.querySelector("input[name='fullName']").value;
        const phoneNumber = document.querySelector("input[name='phoneNumber']").value;
        const email = document.querySelector("input[name='emailBooking']").value;
        const specialRequest = document.querySelector("textarea[name='specialRequest']").value;
        const bookingTourData = {
            dateBook,
            tourDuration,
            typeService,
            hdv,
            quantityAdults,
            quantityChildren,
            totalAmount,
            fullName,
            phoneNumber,
            email,
            specialRequest,
            user,
            tour
        };
        if(totalAmount != 0 ) {

            if (user != null) {
                try {
                    const response = await fetch("http://localhost:8080/api/user/tour/booking/add", {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json"
                        },
                        body: JSON.stringify(bookingTourData)
                    });

                    const data = await response.json();
                    if (data) {
                        BookTourSuccess(true);
                    }
                } catch (error) {
                    console.error("Error:", error);
                }
            } else {
                alert("Bạn cần đăng nhập trước khi đặt tour!");
                loginPopup.style.display = "block";
            }
        } else {
            BookTourSuccess(false);

        }
    });
}

// xu lys modal

function handleModal(){
        const modal = document.getElementById("bookingTourModal");
        const bookNowButton = document.getElementById("BookTour");
        const closeBtn = document.querySelector(".close-btn");

    // Mở modal khi nhấn nút "Đặt ngay"
        bookNowButton.addEventListener("click", function() {
            modal.style.display = "block";
            const quantityAdult = document.querySelector(".quantity-adult");
            const quantityChild = document.querySelector(".quantity-child");
            const dateInput = document.querySelector("#datePickerButton").value;
            document.querySelector(".total-price").textContent = `đ ${totalPrice.toLocaleString('vi-VN')} `;
            if(selectedButtons.tourDuration != null) {
                document.querySelector(".loaiservice").textContent = `
                        ${selectedButtons.typeTour} |  ${selectedButtons.tourDuration}  | ${selectedButtons.hdv}
                ` ;

            } else {
                document.querySelector(".loaiservice").textContent = selectedButtons.typeTour ;
            }
            document.querySelector(".quantiAdult").textContent = `Số Lượng:  ${quantityAdult.value} ` ;
            document.querySelector(".quantiChil").textContent = `Số Lượng:  ${quantityChild.value} ` ;
            document.querySelector("#dateBookShow").value = dateInput ;
        });

        closeBtn.addEventListener("click", function() {
            modal.style.display = "none";
        });

        window.addEventListener("click", function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        });
}
// modal success
function BookTourSuccess(isSuccess){
    const alertModalTour = document.getElementById('alertModalTour');
    const closeModalBtn = document.getElementById('closeModalBtn');
    const successDiv = document.getElementById('success');
    const failDiv = document.getElementById('fail');

    alertModalTour.style.display = 'flex';
    if (isSuccess) {
        successDiv.style.display = 'block';
        failDiv.style.display = 'none';

        closeModalBtn.addEventListener('click', () => {
            alertModalTour.style.display = 'none';
            window.location.reload();


        });

        window.addEventListener('click', (event) => {
            if (event.target === alertModalTour) {
                alertModalTour.style.display = 'none';
                window.location.reload();

            }
        });
    } else {
        successDiv.style.display = 'none';
        failDiv.style.display = 'block';

        closeModalBtn.addEventListener('click', () => {
            alertModalTour.style.display = 'none';
        });

        window.addEventListener('click', (event) => {
            if (event.target === alertModalTour) {
                alertModalTour.style.display = 'none';
            }
        });
    }

}