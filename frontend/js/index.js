/* User related functions */
async function addUser() {
    // Get input fields
    let userNameEl = document.querySelector("#userName");
    let userEmailEl = document.querySelector("#userEmail");
    let userName = userNameEl.value;
    let userEmail = userEmailEl.value;
    // Check input
    if (userName && userEmail) {
        let user = {
            name: userName,
            email: userEmail
        };
        //Add user
        await addObjToDB(user, urls.routeApis.user);
        userNameEl.value = "";
        userEmailEl.value = "";
    } else {
        alert("Input is invalid! Please fill in both fields.");
    }
}

async function displayUsers() {
    let usersObj = await fetch(`${urls.rootUrl}${urls.routeApis.user}`);
    let users = await usersObj.json();
    for (let user of users) {
        if(!document.querySelector(`select#userDropBox option[value="${user.id}"]`)) {
            let newOption = document.createElement("option");
            newOption.setAttribute("value", `${user.id}`);
            newOption.textContent = `${user.name} - ${user.email}`;
            userDropBox.appendChild(newOption);
        } 
    }
}

async function setSessionUser() {
    let sessionId = userDropBox.options[userDropBox.selectedIndex].value;
    if (sessionId != "defaultValue") {
        let userObj = await fetch(`${urls.rootUrl}${urls.routeApis.user}/get/${sessionId}`);
        sessionUser = await userObj.json();
    } else {
        sessionUser = null;
    }
}

/* Product related functions */
async function addProduct() {
    // Get input fields
    let productCodeEl = document.querySelector("#productCode");
    let productNameEl = document.querySelector("#productName");
    let productPriceEl = document.querySelector("#productPrice");
    let productCode = productCodeEl.value;
    let productName = productNameEl.value;
    let productPrice = productPriceEl.value;
    // Check input
    if (productCode && productName && productPrice) {
        let product = {
            productCode: productCode,
            name: productName,
            price: productPrice
        };
        //Add product
        await addObjToDB(product, urls.routeApis.product);
        productCodeEl.value = "";
        productNameEl.value = "";
        productPriceEl.value = "";
    } else {
        alert("Input is invalid! Please fill in all the fields.");
    }
}

async function displayProducts() {
    let productsObj = await fetch(`${urls.rootUrl}${urls.routeApis.product}`);
    let products = await productsObj.json();
    for (let product of products) {
        if(!document.querySelector(`select#productDropBox option[value="${product.id}"]`)) {
            let newOption = document.createElement("option");
            newOption.setAttribute("value", `${product.id}`);
            newOption.textContent = `${product.productCode} - ${product.name}`;
            productDropBox.appendChild(newOption);
        } 
    }
}

async function setSessionProduct() {
    let sessionId = productDropBox.options[productDropBox.selectedIndex].value;
    if (sessionId != "defaultValue") {
        let productObj = await fetch(`${urls.rootUrl}${urls.routeApis.product}/get/${sessionId}`);
        sessionProduct = await productObj.json();
        productPriceEl.innerHTML = `Product price: <b>${sessionProduct.price}$</b>`;
        displayTotalPrice();
    } else {
        productPriceEl.innerHTML = `Product price: <b>0.00$</b>`;
        totalPriceEl.innerHTML = `Total price: <b>0.00$</b>`;
        sessionProduct = null;
    }
}

/* Order related functions */
async function displayTotalPrice() {
    if (sessionProduct != null) {
        let quantity = Number(quantityEl.value);
        let price = Number(sessionProduct.price);
        let totalPrice = quantity * price;
        totalPriceEl.innerHTML = `Total price: <b>${totalPrice.toFixed(2)}$</b>`;
    }
}

async function addOrder() {
    // Get input fields
    let quantity = quantityEl.value;
    let userId;
    let productId;
    try {
        userId = sessionUser.id;
        productId = sessionProduct.id;
    } catch(error) {
        alert("Please select user and product!");
    }
    // Check input
    if (userId && productId && quantity >= 1) {
        let order = {
            userId: userId,
            productId: productId,
            quantity: quantity
        };
        //Add order
        await addObjToDB(order, urls.routeApis.order);
        quantityEl.value = "0";
        displayOrders();
    } else {
        alert("Please select user, product, and number of items. Number of items must be equal to or greater than 1!");
    }
}

async function displayOrders() {
    function addTdElement(newRow, textContent) {
        let newTd = document.createElement("td");
        newTd.textContent = textContent;
        newRow.appendChild(newTd);
    }
    // Delete children
    let child = shoppingCartTable.lastElementChild; 
    while (child) {
        shoppingCartTable.removeChild(child);
        child = shoppingCartTable.lastElementChild;
    }
    // Set session user (use await as it returns promise)
    await setSessionUser();
    // Add new children
    if (sessionUser!=null && !!sessionUser.orders.length) {
        for (let order of sessionUser.orders) {
            let newRow = document.createElement("tr");
            addTdElement(newRow, sessionUser.name);
            addTdElement(newRow, sessionUser.email);
            addTdElement(newRow, order.productCode);
            addTdElement(newRow, order.productName);
            addTdElement(newRow, order.price);
            addTdElement(newRow, order.quantity);
            addTdElement(newRow, String((Number(order.price)*Number(order.quantity)).toFixed(2)));
            let newTd = document.createElement("td");
            let newDelBtn = document.createElement("button");
            newDelBtn.setAttribute("class", "deleteBtn");
            newDelBtn.setAttribute("id", `deleteOrder${order.id}`);
            newDelBtn.textContent = "Delete";
            newTd.appendChild(newDelBtn);
            newRow.appendChild(newTd);
            shoppingCartTable.appendChild(newRow);
        }
    } else {
        return;
    }
}

async function deleteOrder(event) {
    if (!event.target.classList.contains("deleteBtn")) {
        return;
    };
    let orderId = event.target.getAttribute("id").match(/\w+(\d+)/)[1];
    deleteObj(orderId, urls.routeApis.order);
    event.target.closest("tr").remove();
}

/* Common functions */
async function addObjToDB(obj, apiRoute) {
    let objType = apiRoute==="shoppingCart"?"order":apiRoute;
    let resultObj = fetch(`${urls.rootUrl}${apiRoute}/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    }).then(res => {
        if (!res.ok) {
            return res.json()
            .then(error => {throw new Error(`Cannot add ${objType}: ${error.message}`)});
        }
        return res.json();
    })
    .then(data => {
        alert(`${objType} has been successfully added!`);
        return data;
    })
    .catch(err=>alert(err));
    
    return resultObj;
}

async function deleteObj(objId, apiRoute) {
    let objType = apiRoute==="shoppingCart"?"order":apiRoute;
    let isDeleted = fetch(`${urls.rootUrl}${apiRoute}/delete/${objId}`
        , {method: 'DELETE'})
        .then(res => {
            if (!res.ok) {
                return res.json()
                .then(error => {throw new Error(`Cannot delete ${objType}: ${error.message}`)});
            }
            return res;
        })
        .then(data => {
            alert(`${objType} has been successfully deleted!`);
            return true;
        }).catch(err=>{
            alert(err);
            return false; 
        });
    return isDeleted;
}

/* Main code block */
// Setting urls and routes
const urls = {
    rootUrl:"http://localhost:8080/api/",
    routeApis: {
        user:"user",
        product:"product",
        order:"shoppingCart"
    }
}
// Variables
let sessionUser;
let sessionProduct;
let sessionOrder;

/* Define elements */
// User related elements
const addUserBtn = document.querySelector("button#createUserBtn");
const userDropBox = document.querySelector("select#userDropBox");
const userDeleteBtn = document.querySelector("button#deleteUserBtn");
// Product related elements
const addProductBtn = document.querySelector("button#createProductBtn");
const productDropBox = document.querySelector("select#productDropBox");
const productPriceEl = document.querySelector("p#productPrice");
const productDeleteBtn = document.querySelector("button#deleteProductBtn");
// Order related elements
const totalPriceEl = document.querySelector("p#totalPrice");
const quantityEl = document.querySelector("input#quantityInput");
const makeOrderBtn = document.querySelector("button#makeOrderBtn");
const shoppingCartTable = document.querySelector("table#shoppingCartTable tbody");
// Common elements

/* Define functions*/
// User related functions
addUserBtn.addEventListener("click", addUser);
userDropBox.addEventListener("click", displayUsers);
userDropBox.addEventListener("change", displayOrders);
userDeleteBtn.addEventListener("click", async () => {
    let isDeleted = await deleteObj(sessionUser.id, urls.routeApis.user);
    if (isDeleted) {
        document.querySelector(`select#userDropBox option[value="${sessionUser.id}"]`).remove();
    }
});
// Product related functions
addProductBtn.addEventListener("click", addProduct);
productDropBox.addEventListener("click", displayProducts);
productDropBox.addEventListener("change", setSessionProduct);
productDeleteBtn.addEventListener("click", async () => {
    let isDeleted = await deleteObj(sessionProduct.id, urls.routeApis.product);
    console.log(isDeleted);
    if (isDeleted) {
        document.querySelector(`select#productDropBox option[value="${sessionProduct.id}"]`).remove();
    }
});
// Order related functions
quantityEl.addEventListener("change", displayTotalPrice);
makeOrderBtn.addEventListener("click", addOrder);
shoppingCartTable.addEventListener("click", deleteOrder);