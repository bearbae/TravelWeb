//
// Láy thông tin khách sạn

document.addEventListener("DOMContentLoaded", function (){
    fetchTourForAdmin() ;

})
async function fetchTourForAdmin() {
    try {
        const response = await fetch(`http://localhost:8080/api/admin/tour/getAll`);
        if (!response.ok) {
            console.error("Không lấy được thông tin tour. Hãy xem lại trong tour.js");
            return;
        }
        const tour = await response.json();
        console.log(tour);
        displayTourForAdmin(tour);
    } catch (error) {
        console.error("error: ", error);
    }
}

function displayTourForAdmin(data) {
    var tourData = document.getElementById("TourData");
    tourData.innerHTML = "";
    data.forEach( tour =>{
        let formattedPrice = tour.price ? tour.price.toLocaleString('vi-VN') : 'N/A';
        let row =  `
                    <tr>
                        <td>${tour.id}</td>
                        <td>${tour.typeTour}</td>
                        <td>${tour.address}</td>
                        <td>${tour.titleTour}</td>
                        <td>${tour.rateStar}</td>
                        <td>${tour.quantityComment}</td>
                        <td>${tour.booked}</td>
                        <td>${formattedPrice}</td>
                        <td>${tour.highlight}</td>
                        <td style="overflow: hidden;text-overflow: ellipsis;display: -webkit-box;-webkit-box-orient: vertical;
                                -webkit-box-align: start;
                                -webkit-line-clamp: 2;">
                            ${tour.content}
                        </td>
                        <td>
                            <button class="btn btn-view" onclick="viewImage(${tour.id})">Xem Ảnh</button>
                        </td>
                        <td>
                            <button class="btn btn-view" onclick="viewService(${tour.id})">Xem dịch vụ</button>
                        </td>
                        <td>
                            <button class="btn btn-success" onclick="editTour(${tour.id})">Sửa</button>
                            <button class="btn btn-danger" onclick="deleteTour(${tour.id})">Xóa</button>
                        </td>
                    </tr>
            ` ;
        tourData.innerHTML += row ;
    })
}

// Cập nhật tour
function editTour(tourId){
    fetch(`http://localhost:8080/api/admin/tour/getTour/${tourId}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin tour!";
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("AdminEditTour", JSON.stringify(data));
            window.location.href = "/admin/tour/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteTour(tourId){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/admin/tour/delete/${tourId}`, {
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

function viewImage(tourId) {
    console.log(tourId);
    localStorage.setItem("tourIdForImage", JSON.stringify(tourId));
    window.location.href = "/admin/tour/image";
}
function viewService(tourId) {
    localStorage.setItem("tourIdForService", JSON.stringify(tourId));
    window.location.href = "/admin/tour/serviceTour";
}