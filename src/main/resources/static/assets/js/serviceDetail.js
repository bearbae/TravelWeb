//
// Láy thông tin khách sạn

document.addEventListener("DOMContentLoaded", function (){
    fetchServiceTourForAdmin() ;

})
async function fetchServiceTourForAdmin() {
    var tourIdForService = JSON.parse(localStorage.getItem("tourIdForService"));

    if (!tourIdForService) {
        console.error("Không tìm thấy tourIdForImage trong localStorage");
        return "Loi khong co id";
    }
    try {
        const response = await fetch(`http://localhost:8080/api/serviceTour/getByIdTour/${tourIdForService}`);

        if (!response.ok) {
            console.error("Không thể lấy thông tin ảnh!");
            return;
        }
        const serviceTourData = await response.json();
        console.log(serviceTourData);
        displayServiceTour(serviceTourData);
    } catch (error) {
        console.error("Error: ", error);
    }

}

function displayServiceTour(data) {

    var ServiceTourData = document.getElementById("ServiceTourData");
    ServiceTourData.innerHTML = "";
    data.forEach( service =>{
        let formattedPricePerAdult = service.pricePerAdult ? service.pricePerAdult.toLocaleString('vi-VN') : 'N/A';
        let formattedPricePerChild = service.pricePerChild ? service.pricePerChild.toLocaleString('vi-VN') : 'N/A';
        let row =  `
                    <tr>
                        <td>${service.id}</td>
                        <td>${service.timeToGo}</td>
                        <td>${service.timeToBack}</td>
                        <td>${service.time1}</td>
                        <td>${service.address1}</td>
                        <td>${service.time2}</td>
                        <td>${service.address2}</td>
                        <td>${service.time3}</td>
                        <td>${service.address3}</td>
                        <td>${service.vehicle}</td>
                        <td>${service.code}</td>
                        <td>${formattedPricePerAdult}</td>
                        <td>${formattedPricePerChild}</td>
                        <td>
                            <button class="btn btn-success" onclick="editService(${service.id})">Sửa</button>
                            <button class="btn btn-danger" onclick="deleteService(${service.id})">Xóa</button>
                        </td>
                    </tr>
            ` ;
        ServiceTourData.innerHTML += row ;
    })
}

// Cập nhật dichj vuj
function editService(Id){
    fetch(`http://localhost:8080/api/serviceTour/idService/${Id}`)
        .then(response => {
            if(!response.ok){
                return "Không thể lấy thông tin dich vu cua tour!";
            }
            return response.json();
        })
        .then(data => {
            localStorage.setItem("serviceTour", JSON.stringify(data));
            window.location.href = "/admin/tour/serviceTour/update";
        })
        .catch(error => console.error("Error: ",error));
}

function deleteService(Id){
    let confirmation = confirm("Bạn có chắc chắn muốn xóa không?")
    if(confirmation) {
        fetch(`http://localhost:8080/api/serviceTour/delete/${Id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("DeleteError");
                }
                return response.text();
            })
            .then(data => {
                if (data === "Xoa dich vu thanh cong") {
                    window.location.reload();
                } else {
                    alert("Xóa thất bại. Hãy thử lại!")
                }
            })
    }
}
