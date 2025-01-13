document.addEventListener("DOMContentLoaded", function (){
    var hotelData = JSON.parse(localStorage.getItem("hotel"));
    console.log(hotelData);
    let formattedPrice = hotelData.price ? hotelData.price.toLocaleString('vi-VN'): 'N/A';

    if(hotelData){
        let imageName = encodeURIComponent(hotelData.imageHotel);

        document.getElementById("Name").value = hotelData.name ;
        document.getElementById("Location").value = hotelData.location ;
        document.getElementById("Price").value = formattedPrice ;
        document.getElementById("AvailableRooms").value = hotelData.availableRooms ;
        document.getElementById("imageHotel").value = "";
        document.getElementById("description").value = hotelData.description;
        document.getElementById("currentImage").innerHTML = `
            <img style="width: 150px; margin: 5px 0" src="/uploadsHotel/${imageName}" alt="${hotelData.imageHotel}"/>
        `;
    }

    var updateHotel = document.getElementById("updateHotel");
    updateHotel.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formdata = new FormData(form);
        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("AvailableRooms").value)) {
            Message.textContent = "Số phòng trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/hotel/update/${hotelData.id}`, {
            method: 'PUT',
            body: formdata
        }).then(response =>{
            if(!response.ok){
                throw new Error("ErrorUpdate");
            }
            return response.json();
        })
            .then(data => {
                if(data){
                    alert("Cập nhật thành công khách sạn với id = " + hotelData.id);
                    localStorage.removeItem("hotel");
                    window.location.href = "/admin/hotel";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Khách sạn này đã tồn tại. Hãy thử lại!");
            })
    })
})

