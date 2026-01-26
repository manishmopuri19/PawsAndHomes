/* Logic.js - single shared script for index/login/dashboard/adopt pages */
/* Uses localStorage only. Designed to be copy/pasted as-is. */

/* ---------------------------
 DYNAMIC DATA (editable demo content)
 --------------------------- */
 

/* ---------------------------
 Utilities
 --------------------------- */
function showApplicationMessage(html, { duration = 3500, tone = 'info' } = {}) {
const host = document.getElementById('app-message');
if (!host) return;
const icon = tone === 'error' ? 'alert-triangle' : tone === 'success' ? 'check-circle' : 'info';
host.innerHTML = `<div class="p-4 flex items-center gap-3 rounded shadow-lg" style="background:${tone==='error'?'#ef4444':'#1f2937'}; color:white;">
<i data-lucide="${icon}" class="w-5 h-5"></i>
<div class="text-sm">${html}</div>
</div>`;
if (typeof lucide !== 'undefined') lucide.createIcons();
clearTimeout(host._t);
host._t = setTimeout(()=> { host.innerHTML = ''; }, duration);
}

/* ---------------------------
 NAV (active link + mobile toggle)
 --------------------------- */
function initNav() {
// Highlight active link
const path = window.location.pathname.split('/').pop().toLowerCase();
document.querySelectorAll('#main-nav .nav-link, #mobile-nav .nav-link').forEach(a => {
const href = (a.getAttribute('href')||'').split('/').pop().toLowerCase();
a.classList.remove('active-link');
if (href && href === path) a.classList.add('active-link');
});

// Mobile nav toggle with smooth animation
const menuBtn = document.getElementById('menu-toggle');
const mobile = document.getElementById('mobile-nav');
if (menuBtn && mobile) {
menuBtn.addEventListener('click', () => mobile.classList.toggle('hidden'));
}

// 🪄 Page fade-in animation
window.addEventListener("load", () => {
document.body.classList.add("page-loaded");
});
}


/* ---------------------------
 Stray report modal (file input, success)
 --------------------------- */
function openStrayReportModal() {
const container = document.getElementById('stray-report-modal');
if (!container) return;

container.innerHTML = `
<div class="fixed inset-0 bg-gray-900/60 z-50 flex items-center justify-center p-4" onclick="event.stopPropagation()">
<div class="bg-white rounded-2xl shadow-2xl w-full max-w-lg p-6" onclick="event.stopPropagation()">
<div class="flex justify-between items-center mb-4">
<h3 class="text-xl font-bold text-red-600 flex items-center gap-2">
<i data-lucide="alert-triangle" class="w-5 h-5"></i> Report Stray Animal
</h3>
<button id="close-stray" class="text-gray-500">
<i data-lucide="x" class="w-6 h-6"></i>
</button>
</div>
<form id="stray-form">
<label class="block text-sm font-medium text-gray-700 mb-1">Mobile Number</label>
<input required type="tel" name="mobile" pattern="[0-9]{10}" maxlength="10" class="w-full p-3 border rounded-lg mb-3" placeholder="Enter your 10-digit mobile number"/>

<label class="block text-sm font-medium text-gray-700 mb-1">Location</label>
<input required name="location" class="w-full p-3 border rounded-lg mb-3" placeholder="Address or landmark"/>

<label class="block text-sm font-medium text-gray-700 mb-1">Details</label>
<textarea required name="details" rows="3" class="w-full p-3 border rounded-lg mb-3" placeholder="Describe condition / urgency"></textarea>

<div>
<label class="block text-sm font-medium text-gray-700 mb-1">Upload Picture</label>
<input type="file" id="stray-photo" accept="image/png, image/jpeg" required class="block w-full text-sm text-gray-700 border border-gray-300 rounded-lg cursor-pointer p-2"/>
</div>

<div class="flex gap-3 mt-4">
<button type="submit" class="bg-red-600 text-white px-4 py-2 rounded-xl font-semibold">Submit Report</button>
<button type="button" id="cancel-stray" class="px-4 py-2 rounded-xl border">Cancel</button>
</div>
</form>
</div>
</div>
`;

if (typeof lucide !== 'undefined') lucide.createIcons();
container.classList.remove('hidden');

document.getElementById('close-stray').onclick = () => { 
container.classList.add('hidden'); 
container.innerHTML=''; 
};
document.getElementById('cancel-stray').onclick = () => { 
container.classList.add('hidden'); 
container.innerHTML=''; 
};

document.getElementById('stray-form').onsubmit = (e) => {
e.preventDefault();
const f = e.target;
const mobile = f.mobile.value.trim();
const loc = f.location.value.trim();
const details = f.details.value.trim();

if (!/^[0-9]{10}$/.test(mobile)) {
showApplicationMessage('Please enter a valid 10-digit mobile number.', { tone: 'error' });
return;
}

if (!loc || !details) { 
showApplicationMessage('Please provide both location and details.', { tone: 'error' }); 
return; 
}

// Store in localStorage (optional)
const reports = JSON.parse(localStorage.getItem('strayReports') || '[]');
reports.push({
id: Date.now(),
mobile,
location: loc,
details,
time: new Date().toISOString(),
});
localStorage.setItem('strayReports', JSON.stringify(reports));

container.classList.add('hidden'); 
container.innerHTML = '';
showApplicationMessage(`Thanks — report submitted for <strong>${loc}</strong>. Nearby NGO alerted.`, { tone: 'success' });
};
}


/* ---------------------------
 Auth: register/login (localStorage)
 - registration stores user in 'users'
 - login checks credentials; if not found, auto-create (duplicate-capable)
 --------------------------- */
function initAuthPages() {
const loginForm = document.getElementById('login-form');
const userTypeSwitch = document.getElementById('user-type-switch');
const errorBox = document.getElementById('error-message');

// ✅ Predefined users
const users = {
adopter: {
email: "user1@gmail.com",
password: "user123",
name: "Adopter User"
},
ngo: {
email: "ngo@gmail.com",
password: "ngo123",
orgname: "Paws & Homes NGO"
}
};

if (!loginForm) return;

loginForm.addEventListener("submit", e => {
e.preventDefault();

const email = loginForm.email.value.trim();
const password = loginForm.password.value.trim();
const isNGO = userTypeSwitch?.classList.contains("is-ngo");

const currentType = isNGO ? "ngo" : "adopter";
const validUser = users[currentType];

if (email === validUser.email && password === validUser.password) {
const loggedInUser = {
type: currentType,
email,
name: validUser.name || validUser.orgname
};

// Save current user only
localStorage.setItem("loggedInUser", JSON.stringify(loggedInUser));

// Redirect
const redirectPage = currentType === "ngo" ? "NgoDashboard.html" : "index.html";
window.location.href = redirectPage;
} else {
// Show safe inline error
errorBox.textContent = "Invalid email or password. Please try again.";
errorBox.classList.remove("hidden");
}
});
}

// Run when page ready
window.addEventListener("DOMContentLoaded", initAuthPages);

/* ---------------------------
 Index/Dashboard shared dynamic UI
 - show profile dropdown on index when logged in
 - adapt header nav
 --------------------------- */
function renderHeaderProfile() {
const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser') || 'null');
const loginBtn = document.getElementById('login-btn');
const userProfileContainer = document.getElementById('user-profile');

if (!loginBtn || !userProfileContainer) return;

if (loggedInUser) {
loginBtn.style.display = 'none';
userProfileContainer.style.display = 'block';

// Determine correct dashboard page
const dashboardLink =
loggedInUser.type === 'ngo' ? 'NgoDashboard.html' : 'dashboard.html';

userProfileContainer.innerHTML = `
<div class="relative">
<button id="profile-btn" class="flex items-center gap-2 bg-gray-200 hover:bg-gray-300 px-4 py-2 rounded-full font-semibold">
<i data-lucide="user" class="w-5 h-5"></i>
<span id="username-display">
${loggedInUser.name || loggedInUser.orgname || (loggedInUser.email || 'User').split('@')[0]}
</span>
<i data-lucide="chevron-down" class="w-4 h-4"></i>
</button>
<div id="profile-menu" class="hidden absolute right-0 mt-2 w-56 bg-white border rounded-lg shadow-lg py-2 z-30">
<a href="${dashboardLink}" class="block px-4 py-2 hover:bg-indigo-50">Dashboard</a>
<button id="logout-btn" class="w-full text-left px-4 py-2 hover:bg-red-50 text-red-600">Logout</button>
</div>
</div>
`;

if (typeof lucide !== 'undefined') lucide.createIcons();

// toggle dropdown
document.getElementById('profile-btn').addEventListener('click', () => {
document.getElementById('profile-menu').classList.toggle('hidden');
});

// logout
document.getElementById('logout-btn').addEventListener('click', () => {
localStorage.removeItem('loggedInUser');
renderHeaderProfile(); // refresh header
window.location.href = 'login.html';
});
} else {
loginBtn.style.display = 'block';
userProfileContainer.style.display = 'none';
userProfileContainer.innerHTML = '';
}
}


/* ---------------------------
 Adopt page helpers (save adopted pet linking to logged user)
 --------------------------- */

/** Global function to handle the adoption process after form submission */
function finalizeAdoption(petId, adopterDetails) {
    const PET_POSTS_KEY = 'availablePets'; 
    const ADOPTED_KEY = 'adoptedPets'; 
    const currentAvailablePets = JSON.parse(localStorage.getItem(PET_POSTS_KEY) || '[]');
    const adoptedPets = JSON.parse(localStorage.getItem(ADOPTED_KEY) || '[]');
    
    const petIndex = currentAvailablePets.findIndex(p => p.id === petId);
    if (petIndex === -1) {
        showApplicationMessage('Error: Pet not found.', { tone: 'error' });
        return;
    }

    const pet = currentAvailablePets[petIndex];
    
    // 1. Store adoption in 'adoptedPets' list
    adoptedPets.push({ 
 ...pet, 
 user: adopterDetails.name, // Use name from form
 adopterEmail: adopterDetails.email,
       adopterMobile: adopterDetails.mobile,
       adopterLocation: adopterDetails.location,
 adoptedAt: new Date().toISOString() 
});
    localStorage.setItem(ADOPTED_KEY, JSON.stringify(adoptedPets));
    showApplicationMessage(`Adoption request for <strong>${pet.name}</strong> submitted! NGO will contact you at ${adopterDetails.mobile}.`, { tone: 'success' });
    
    // 2. Remove the pet from 'availablePets' list
    currentAvailablePets.splice(petIndex, 1);
    localStorage.setItem(PET_POSTS_KEY, JSON.stringify(currentAvailablePets));

    // 3. Re-render the grid
    const grid = document.getElementById('pet-card-grid');
    if (grid) {
        // We need to re-fetch the rendering function logic to update the page
        // For this simplified Logic.js, we'll force a render:
        window.location.reload(); 
    }
}

/** Function to open the adoption details modal */
function openAdoptionModal(pet) {
    const container = document.getElementById('stray-report-modal'); // Reusing this generic modal container
    if (!container) return;

    // Get logged-in user details to pre-fill form
    const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser') || 'null');

    container.innerHTML = `
        <div class="fixed inset-0 bg-gray-900/60 z-50 flex items-center justify-center p-4" onclick="event.stopPropagation()">
          <div class="bg-white rounded-2xl shadow-2xl w-full max-w-md p-6" onclick="event.stopPropagation()">
            <div class="flex justify-between items-center mb-4">
              <h3 class="text-xl font-bold text-indigo-600 flex items-center gap-2">
                <i data-lucide="heart" class="w-5 h-5"></i> Adopt ${pet.name}
              </h3>
              <button id="close-adopt-modal" class="text-gray-500">
                <i data-lucide="x" class="w-6 h-6"></i>
              </button>
            </div>
            <p class="text-sm text-gray-600 mb-4">Please enter your contact details so the NGO can finalize the adoption.</p>
            <form id="adoption-form" data-pet-id="${pet.id}">
              <label class="block text-sm font-medium text-gray-700 mb-1">Your Full Name</label>
              <input required type="text" name="name" class="w-full p-3 border rounded-lg mb-3" placeholder="Full Name" value="${loggedInUser ? loggedInUser.name || '' : ''}"/>
              
              <label class="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input required type="email" name="email" class="w-full p-3 border rounded-lg mb-3" placeholder="Email Address" value="${loggedInUser ? loggedInUser.email || '' : ''}"/>
              
              <label class="block text-sm font-medium text-gray-700 mb-1">Mobile Number</label>
              <input required type="tel" name="mobile" pattern="[0-9]{10}" maxlength="10" class="w-full p-3 border rounded-lg mb-3" placeholder="10-digit mobile number"/>

              <label class="block text-sm font-medium text-gray-700 mb-1">Location/City</label>
              <input required type="text" name="location" class="w-full p-3 border rounded-lg mb-3" placeholder="Your City/Location"/>
              
              <div class="flex gap-3 mt-4">
                <button type="submit" class="bg-indigo-600 text-white px-4 py-2 rounded-xl font-semibold">Confirm Adoption Request</button>
                <button type="button" id="cancel-adopt" class="px-4 py-2 rounded-xl border">Cancel</button>
              </div>
            </form>
          </div>
        </div>
    `;

    if (typeof lucide !== 'undefined') lucide.createIcons();
    container.classList.remove('hidden');

    const closeModal = () => { container.classList.add('hidden'); container.innerHTML = ''; };
    document.getElementById('close-adopt-modal').onclick = closeModal;
    document.getElementById('cancel-adopt').onclick = closeModal;

    document.getElementById('adoption-form').onsubmit = (e) => {
        e.preventDefault();
        const f = e.target;
        const petId = Number(f.getAttribute('data-pet-id'));

        const adopterDetails = {
            name: f.name.value.trim(),
            email: f.email.value.trim(),
            mobile: f.mobile.value.trim(),
            location: f.location.value.trim()
        };

        if (!/^[0-9]{10}$/.test(adopterDetails.mobile)) {
    showApplicationMessage('Please enter a valid 10-digit mobile number.', { tone: 'error' });
    return;
    }

        closeModal();
        // Call the function to finalize the adoption process
        finalizeAdoption(petId, adopterDetails);
    };
}


function initAdoptPage() {
 const grid = document.getElementById('pet-card-grid');
 const noPetsMsg = document.getElementById('no-pets-message');
 if (!grid) return;

 // 🔑 Fetch pets from the 'availablePets' key in localStorage
 function getAvailablePets() {
// Fallback if the key doesn't exist, we use an empty array.
return JSON.parse(localStorage.getItem('availablePets') || '[]');
 }
 
 function renderPets(list) {
    if (list.length === 0) {
        grid.innerHTML = '';
        noPetsMsg.classList.remove('hidden');
        return;
    }

    noPetsMsg.classList.add('hidden');

    grid.innerHTML = list.map(p => `
        <div class="bg-white rounded-2xl pet-card shadow-xl hover:shadow-2xl transition duration-300 transform hover:-translate-y-1" data-id="${p.id}">
            <div class="relative h-60 w-full">
                <img src="${p.img}" alt="${p.name}" class="w-full h-full object-cover rounded-t-2xl"/>
                <span class="absolute top-4 left-4 bg-indigo-600 text-white text-xs font-bold px-3 py-1.5 rounded-full shadow-lg">
                    ${p.type} </span>
            </div>
            <div class="p-5 flex flex-col gap-3">
                <div>
                    <h4 class="text-3xl font-extrabold text-gray-800">${p.name}</h4>
                    <p class="text-base font-medium text-gray-500 mt-1">${p.breed}</p>
                </div>
                
                <div class="flex items-center gap-2 text-sm flex-wrap pt-2 border-t border-gray-100">
                    <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded-lg font-semibold">${p.age} yrs</span>
                    <span class="px-3 py-1 bg-green-100 text-green-700 rounded-lg font-semibold">${p.gender}</span>
                    <span class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded-lg font-semibold">${p.size ? p.size : 'Size N/A'}</span>
                </div>
            </div>
            <div class="p-4 pt-0">
                <div class="flex gap-3">
                    <button class="w-3/4 adopt-btn px-4 py-3 rounded-xl bg-indigo-600 text-white font-bold hover:bg-indigo-700 transition" 
                        data-action="adopt" data-id="${p.id}">
                        Adopt ${p.name}
                    </button>
                    <button class="w-1/4 border border-gray-300 rounded-xl px-4 py-3 text-gray-700 font-semibold hover:bg-gray-50 transition" 
                        data-action="view" data-id="${p.id}">
                        Details
                    </button>
                </div>
            </div>
        </div>
    `).join('');
}
 // Initial rendering using the available pets from localStorage
 let currentAvailablePets = getAvailablePets();
 renderPets(currentAvailablePets);

 grid.addEventListener('click', (ev) => {
const btn = ev.target.closest('button[data-action]');
if (!btn) return;
const id = Number(btn.dataset.id);

// Always fetch the latest list before finding the pet
currentAvailablePets = getAvailablePets(); 
const pet = currentAvailablePets.find(p => p.id === id);
if (!pet) return;

if (btn.dataset.action === 'adopt') {
 const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser')||'null');
 
 if (loggedInUser) {
        // ⭐ NEW LOGIC: Open the adoption form modal instead of finalizing immediately
        openAdoptionModal(pet);
 } else {
showApplicationMessage('Please login to adopt.', { tone: 'error' });
return;
 }

} else {
 // view modal logic is large, simplifying to just open the modal with pet data
 const host = document.getElementById('stray-report-modal');
 host.innerHTML = `
<div class="fixed inset-0 z-50 bg-black/70 flex items-center justify-center p-4">
    <div class="bg-white rounded-2xl w-full max-w-3xl shadow-2xl relative">
        
        <button id="close-pet-modal" class="absolute top-4 right-4 text-gray-500 hover:text-gray-900 transition z-10">
            <i data-lucide="x" class="w-7 h-7"></i>
        </button>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6 p-6" >
            
            <div class="md:col-span-1">
                <img src="${pet.img}" class="w-full h-72 object-cover rounded-xl shadow-lg mb-4" alt="${pet.name}" />
                
                <h4 class="text-lg font-bold text-gray-800 mb-2 border-b pb-1">Quick Facts</h4>
                <div class="flex flex-wrap gap-2 text-sm">
                    <span class="px-3 py-1 bg-blue-100 text-blue-700 rounded-lg font-semibold">${pet.age}</span>
                    <span class="px-3 py-1 bg-green-100 text-green-700 rounded-lg font-semibold">${pet.gender}</span>
                    <span class="px-3 py-1 bg-yellow-100 text-yellow-700 rounded-lg font-semibold">${pet.size}</span>
                    <span class="px-3 py-1 bg-indigo-100 text-indigo-700 rounded-lg font-semibold">${pet.type}</span>
                </div>
            </div>

            <div class="md:col-span-1 flex flex-col">
                <h3 class="text-4xl font-extrabold text-gray-900 mb-1">${pet.name}</h3>
                <p class="text-xl text-gray-600 mb-4 border-b pb-2">(${pet.breed})</p>
                
                <h4 class="text-lg font-bold text-gray-800 mt-2 mb-2">My Story:</h4>
                <p class="text-gray-700 mb-6 flex-grow">${pet.desc}</p>
                
                <div class="mt-auto flex gap-3 pt-4 border-t border-gray-100">
                    <button id="adopt-from-modal" data-id="${pet.id}" 
                            class="flex-1 bg-indigo-600 text-white px-4 py-3 rounded-xl font-bold text-lg hover:bg-indigo-700 transition">
                        Adopt ${pet.name}
                    </button>
                    <button id="close-from-modal" 
                            class="w-1/4 px-4 py-3 rounded-xl border border-gray-300 text-gray-700 font-semibold hover:bg-gray-50 transition">
                        Close
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
`;
 if (typeof lucide !== 'undefined') lucide.createIcons();
 host.classList.remove('hidden');

 const closeModal = () => { host.classList.add('hidden'); host.innerHTML=''; };
 document.getElementById('close-pet-modal').onclick = closeModal;
 document.getElementById('close-from-modal').onclick = closeModal;

 document.getElementById('adopt-from-modal').onclick = () => {
// Same adopt logic, but getting the pet ID from the modal button
const petId = Number(document.getElementById('adopt-from-modal').dataset.id);
const loggedInUser = JSON.parse(localStorage.getItem('loggedInUser')||'null');
const petToAdopt = getAvailablePets().find(p => p.id === petId);

if (loggedInUser && petToAdopt) {
            // ⭐ NEW LOGIC: Open the adoption form modal instead of finalizing immediately
            closeModal();
            openAdoptionModal(petToAdopt);
} else {
 showApplicationMessage('Please login to adopt.', { tone: 'error' });
}
 };
}
 }
 );
}

/* ---------------------------
 Dashboard init (edit profile, documents, adopted pets)
 - Edit profile intentionally duplicates a user entry (per request)
 - Documents stored in 'documents' list
 --------------------------- */

/* ---------------------------
 Generic page boot
 --------------------------- */
document.addEventListener('DOMContentLoaded', () => {
if (typeof lucide !== 'undefined') lucide.createIcons();

initNav();
renderHeaderProfile();
initAuthPages();

// attach stray report modal global
window.openStrayReportModal = openStrayReportModal;

// attach adopt page
if (document.getElementById('pet-card-grid')) {
try { initAdoptPage(); } catch (e) { console.error(e); }
}

// attach dashboard if on dashboard page
if (document.getElementById('user-name')) {
try { initDashboard(); } catch (e) { console.error(e); }
}

// attach index featured pets if present
const fh = document.getElementById('featured-pets');
if (fh) {
const SITE_DATA = { pets: JSON.parse(localStorage.getItem('availablePets') || '[]') };
fh.innerHTML = SITE_DATA.pets.slice(0,3).map(p => `
<div class="bg-white rounded-xl shadow p-4 flex gap-4 items-center">
<img src="${p.img}" class="w-28 h-20 object-cover rounded-md" alt="${p.name}" />
<div><h4 class="font-semibold">${p.name}</h4><div class="text-xs text-gray-500">${p.species} • ${p.breed}</div></div>
<div class="ml-auto text-indigo-600 font-semibold">Adopt</div>
</div>
`).join('');
}
});