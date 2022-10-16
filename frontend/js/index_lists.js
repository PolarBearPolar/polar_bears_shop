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
        let userObj = await addUserToDB(user);
        userNameEl.value = "";
        userEmailEl.value = "";
    } else {
        alert("Input is invalid! Please fill in both fields.");
    }
}

async function addUserToDB(user) {
    let userObj = await fetch(`${url}user/add`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
    });
    let result = await userObj.json();
    return result;
}

async function displayUsers() {
    function display(elIdValue, displayName) {
        //let newEl = document.createElement("li");
        //newEl.id = `user${user.id}`;
        //newEl.textContent = `User: id - ${user.id},  name - ${user.name},  email - ${user.email}`;
        //displayUserEl.appendChild(newEl);
        if (!displayUserEl.querySelector(`input#${elIdValue}`)) {
            let newRadio = document.createElement("input");
            let newLabel = document.createElement("label");
            let newBr = document.createElement("br");
            newRadio.id = `${elIdValue}`;
            newRadio.setAttribute("type", "radio");
            newRadio.setAttribute("name", "user");
            newLabel.setAttribute("for", `${elIdValue}`);
            newLabel.textContent = displayName;
            displayUserEl.appendChild(newRadio);
            displayUserEl.appendChild(newLabel);
            displayUserEl.appendChild(newBr);
        } else {
            ;
        }
    }
    let usersObj = await fetch(`${url}user`);
    let users = await usersObj.json();
    display("allUsers", "User: all users");
    for (let user of users) {
        display(`user${user.id}`, `User: id - ${user.id},  name - ${user.name},  email - ${user.email}`);
    }
}

const url = "http://localhost:8080/api/";
const addUserBtn = document.querySelector("#createUserBtn");
const displayUserEl = document.querySelector("#displayUsers ul");
const displayUserBtn = document.querySelector("#displayUserBtn");
addUserBtn.addEventListener("click", addUser);
displayUserBtn.addEventListener("click", displayUsers);