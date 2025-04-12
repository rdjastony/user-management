const API_BASE = "http://127.0.0.1:61298/users";

function ping() {
  fetch(`${API_BASE}/ping`)
    .then(res => res.text())
    .then(data => document.getElementById("pingResult").innerText = data)
    .catch(err => console.error(err));
}

function getUser() {
  const id = document.getElementById("getUserId").value;
  fetch(`${API_BASE}/${id}`)
    .then(res => res.json())
    .then(data => document.getElementById("userResult").innerText = JSON.stringify(data, null, 2))
    .catch(err => console.error(err));
}

function addUser() {
  const user = {
    id: document.getElementById("userId").value,
    name: document.getElementById("userName").value,
    email: document.getElementById("userEmail").value,
    phone: document.getElementById("userPhone").value
  };

  fetch(API_BASE, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify([user]) // Sending as array (for bulk too)
  })
    .then(res => res.json())
    .then(data => document.getElementById("addResult").innerText = JSON.stringify(data, null, 2))
    .catch(err => console.error(err));
}

function deleteUser() {
  const id = document.getElementById("deleteUserId").value;
  fetch(`${API_BASE}/${id}`, {
    method: "DELETE"
  })
    .then(res => res.json())
    .then(data => document.getElementById("deleteResult").innerText = JSON.stringify(data, null, 2))
    .catch(err => console.error(err));
}
