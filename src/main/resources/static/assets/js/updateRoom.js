document.addEventListener("DOMContentLoaded", function (){

    var roomData = JSON.parse(localStorage.getItem("room"));
    // console.log(roomData.hotel.id);
    console.log(roomData);
    let formattedPrice = roomData.pricePerNight ? roomData.pricePerNight.toLocaleString('vi-VN'): 'N/A';

    if(roomData){
        let imageName = encodeURIComponent(roomData.imageRoom);

        document.getElementById("roomName").value = roomData.roomName ;
        document.getElementById("roomType").value = roomData.roomType ;
        document.getElementById("view").value = roomData.view ;
        document.getElementById("area").value = roomData.area ;
        document.getElementById("pricePerNight").value = formattedPrice ;
        document.getElementById("amenities").value = roomData.amenities ;
        // document.getElementById("currentImage").textContent = roomData.imageRoom || "Không có ảnh";
        document.getElementById("currentImage").innerHTML =`
            <img style="width: 150px; margin: 5px 0" src="/uploadsRoom/${imageName}" alt="${roomData.imageRoom}"/>
        `;
        document.getElementById("quantity").value = roomData.quantity ;


    }
    localStorage.removeItem("room");

    var updateRoom = document.getElementById("updateRoom");
    updateRoom.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        console.log(formData);

        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("quantity").value)) {
            Message.textContent = "Số phòng trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/hotel/room/update/${roomData.id}`, {
            method: 'PUT',
            body: formData
        }).then(response =>{
            if(!response.ok){
                throw new Error("ErrorUpdate");
            }
            return response.json();
        })
            .then(data => {
                if(data){
                    alert("Cập nhật thành công!")
                    window.location.href = "/admin/hotel/room";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Phong này đã tồn tại. Hãy thử lại!");
            })
    })
})

