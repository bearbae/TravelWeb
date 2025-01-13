document.addEventListener("DOMContentLoaded", function (){
    var restaurantData = JSON.parse(localStorage.getItem("restaurant"));
    console.log(restaurantData);
    let formattedPrice = restaurantData.price ? restaurantData.price.toLocaleString('vi-VN'): 'N/A';

    if(restaurantData){
        document.getElementById("Name").value = restaurantData.name ;
        document.getElementById("Location").value = restaurantData.location ;
        document.getElementById("Price").value = formattedPrice ;
        document.getElementById("AvailableTables").value = restaurantData.availableTables ;
        document.getElementById("imageRestaurant").value = "";
        document.getElementById("description").value = restaurantData.description;
        document.getElementById("currentImage").textContent = restaurantData.imageRestaurant || "Không có ảnh";
    }

    var updateRestaurant = document.getElementById("updateRestaurant");
    updateRestaurant.addEventListener("submit", function (event){
        event.preventDefault();
        const form = event.target;
        const formdata = new FormData(form);

        const Message = document.getElementById("message");
        Message.textContent = "";
        Message.classList.remove("text-danger", "text-success");
        if (!/^\d+$/.test(document.getElementById("AvailableTables").value)) {
            Message.textContent = "Số bàn trống chỉ được nhập ký tự số";
            Message.classList.add("text-danger");
            return;
        }
        fetch(`http://localhost:8080/api/admin/restaurant/update/${restaurantData.id}`, {
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
                    alert("Cập nhật thành công nhà hàng với id = " + restaurantData.id);
                    localStorage.removeItem("restaurant");
                    window.location.href = "/admin/restaurant";
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Nhà hàng sạn này đã tồn tại. Hãy thử lại!");
            }) 
    })
})

