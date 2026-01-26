  // Sidebar navigation
  const navItems = document.querySelectorAll('.nav-item');
  const slider = document.getElementById('nav-slider');
  const sections = ['profile-section', 'reports-section', 'donations-section', 'adoptions-section'];

  function updateSlider(index) {
    const item = navItems[index];
    slider.style.top = item.offsetTop + 'px';
    slider.style.height = item.offsetHeight + 'px';
    sections.forEach((id, i) => {
      document.getElementById(id).classList.toggle('hidden', i !== index);
    });
  }
  navItems.forEach((item, idx) => item.onclick = () => updateSlider(idx));
  updateSlider(0);

  // Posts
  const postModal = document.getElementById('post-modal');
  const openPostBtn = document.getElementById('open-post-modal');
  const closePostBtn = document.getElementById('close-modal');
  const postForm = document.getElementById('post-form');
  const postsContainer = document.getElementById('posts-container');
  let posts = JSON.parse(localStorage.getItem('ngoPosts') || '[]');

  function renderPosts() {
    postsContainer.innerHTML = '';
    posts.forEach((post,index)=>{
      const div = document.createElement('div');
      div.className='post-card';
      div.innerHTML=`
        ${post.image?`<img src="${post.image}" class="post-image">`:''}
        <div class="post-content">
          <p class="post-text">${post.text}</p>
          <div class="flex justify-between text-sm mt-2">
            <div class="flex gap-6">
              <div class="btn-action like-btn ${post.liked?'active':''}" data-index="${index}">
                <i data-lucide="heart"></i> <span>${post.likes}</span>
              </div>
              <div class="btn-action comment-toggle" data-index="${index}"><i data-lucide="message-square"></i> Comments</div>
              <div class="btn-action share-btn" data-index="${index}"><i data-lucide="share-2"></i> Share</div>
            </div>
            <div class="btn-action delete-btn" data-index="${index}"><i data-lucide="trash-2"></i> Delete</div>
          </div>
          <div class="comment-box" id="comments-${index}">
            <input type="text" class="w-full p-2 border rounded mt-2" placeholder="Add a comment...">
            <div class="comments-list mt-2">
              ${post.comments.map(c=>`<p class="comment">💬 ${c}</p>`).join('')}
            </div>
          </div>
        </div>
      `;
      postsContainer.prepend(div);
    });
    lucide.createIcons();
    addPostListeners();
  }

  function addPostListeners(){
    document.querySelectorAll('.like-btn').forEach(btn=>{
      btn.onclick=()=>{
        const i=btn.dataset.index;
        posts[i].liked=!posts[i].liked;
        posts[i].likes+=posts[i].liked?1:-1;
        savePosts();
      }
    });
    document.querySelectorAll('.comment-toggle').forEach(btn=>{
      btn.onclick=()=>{
        const i=btn.dataset.index;
        const box=document.getElementById(`comments-${i}`);
        box.style.display=box.style.display==='block'?'none':'block';
        const input=box.querySelector('input');
        input.onkeypress=e=>{
          if(e.key==='Enter' && input.value.trim()){
            posts[i].comments.push(input.value.trim());
            input.value='';
            savePosts();
          }
        }
      }
    });
    document.querySelectorAll('.share-btn').forEach(btn=>{
      btn.onclick=()=>alert('Post shared successfully!');
    });
    document.querySelectorAll('.delete-btn').forEach(btn=>{
      btn.onclick=()=>{
        const i=btn.dataset.index;
        if(confirm('Delete this post?')){
          posts.splice(i,1);
          savePosts();
        }
      }
    });
  }

  function savePosts(){
    localStorage.setItem('ngoPosts',JSON.stringify(posts));
    renderPosts();
  }

  openPostBtn.onclick=()=>postModal.style.display='flex';
  closePostBtn.onclick=()=>postModal.style.display='none';
  postForm.onsubmit=e=>{
    e.preventDefault();
    const text=document.getElementById('post-text').value.trim();
    const imageInput=document.getElementById('post-image');
    if(!text && !imageInput.files.length) return;
    const newPost={text,image:'',likes:0,comments:[],liked:false};
    if(imageInput.files[0]){
      const reader=new FileReader();
      reader.onload=e=>{
        newPost.image=e.target.result;
        posts.push(newPost);
        savePosts();
      }
      reader.readAsDataURL(imageInput.files[0]);
    }else{
      posts.push(newPost);
      savePosts();
    }
    postForm.reset();
    postModal.style.display='none';
  }
  renderPosts();

  // Adoptions
  const adoptModal = document.getElementById('adopt-modal');
  const openAdoptBtn = document.getElementById('open-adopt-modal');
  const closeAdoptBtn = document.getElementById('close-adopt-modal');
  const adoptForm = document.getElementById('adopt-form');
  const adoptionsContainer = document.getElementById('adoptions-container');
  let adoptions = JSON.parse(localStorage.getItem('ngoAdoptions') || '[]');

  function renderAdoptions() {
    adoptionsContainer.innerHTML = '';
    adoptions.forEach((pet,index)=>{
      const div=document.createElement('div');
      div.className='post-card';
      div.innerHTML=`
        <img src="${pet.image}" class="post-image">
        <div class="post-content">
          <p><strong>Name:</strong> ${pet.name}</p>
          <p><strong>Age:</strong> ${pet.age}</p>
          <p><strong>Gender:</strong> ${pet.gender}</p>
          <div class="btn-action delete-btn" data-index="${index}"><i data-lucide="trash-2"></i> Delete</div>
        </div>
      `;
      adoptionsContainer.prepend(div);
    });
    lucide.createIcons();
    document.querySelectorAll('#adoptions-container .delete-btn').forEach(btn=>{
      btn.onclick=()=>{
        const i=btn.dataset.index;
        if(confirm('Remove this pet?')){
          adoptions.splice(i,1);
          saveAdoptions();
        }
      }
    });
  }

  function saveAdoptions(){
    localStorage.setItem('ngoAdoptions',JSON.stringify(adoptions));
    renderAdoptions();
  }

  openAdoptBtn.onclick=()=>adoptModal.style.display='flex';
  closeAdoptBtn.onclick=()=>adoptModal.style.display='none';

  adoptForm.onsubmit=e=>{
    e.preventDefault();
    const name=document.getElementById('pet-name').value.trim();
    const age=document.getElementById('pet-age').value.trim();
    const gender=document.getElementById('pet-gender').value;
    const imageInput=document.getElementById('pet-image');
    if(!name || !age || !gender || !imageInput.files.length) return;
    const newPet={name,age,gender,image:''};
    const reader=new FileReader();
    reader.onload=e=>{
      newPet.image=e.target.result;
      adoptions.push(newPet);
      saveAdoptions();
    }
    reader.readAsDataURL(imageInput.files[0]);
    adoptForm.reset();
    adoptModal.style.display='none';
  }
  renderAdoptions();

  // Home & Logout// Donations
const donationModal = document.getElementById('donation-modal');
const openDonationBtn = document.getElementById('open-donation-modal');
const closeDonationBtn = document.getElementById('close-donation-modal');
const donationForm = document.getElementById('donation-form');
const donationsContainer = document.getElementById('donations-container');
const donationCountEl = document.getElementById('donation-count');

let donations = JSON.parse(localStorage.getItem('ngoDonations') || '[]');

function renderDonations() {
  donationsContainer.innerHTML = '';
  donations.forEach((donation, index) => {
    const div = document.createElement('div');
    div.className = 'post-card flex items-center gap-4';
    div.innerHTML = `
      <img src="${donation.qr}" class="post-image w-32 h-32">
      <div class="flex-1 flex justify-between items-center">
        <p>Donation QR Code #${index+1}</p>
        <div class="btn-action delete-btn" data-index="${index}"><i data-lucide="trash-2"></i> Delete</div>
      </div>
    `;
    donationsContainer.prepend(div);
  });
  donationCountEl.textContent = donations.length;
  lucide.createIcons();

  document.querySelectorAll('#donations-container .delete-btn').forEach(btn => {
    btn.onclick = () => {
      const i = btn.dataset.index;
      if(confirm('Remove this QR Code?')) {
        donations.splice(i,1);
        saveDonations();
      }
    }
  });
}

function saveDonations() {
  localStorage.setItem('ngoDonations', JSON.stringify(donations));
  renderDonations();
}

openDonationBtn.onclick = () => donationModal.style.display = 'flex';
closeDonationBtn.onclick = () => donationModal.style.display = 'none';

donationForm.onsubmit = e => {
  e.preventDefault();
  const imageInput = document.getElementById('donation-qr');
  if(!imageInput.files.length) return;
  const reader = new FileReader();
  reader.onload = e => {
    donations.push({qr: e.target.result});
    saveDonations();
    donationForm.reset();
    donationModal.style.display = 'none';
  }
  reader.readAsDataURL(imageInput.files[0]);
}

renderDonations();

  document.getElementById('homeBtn').onclick = () => window.location.href='index.html';
  document.getElementById('logoutBtn').onclick = () => {
    localStorage.removeItem('loggedInUser');
    window.location.href='login.html';
  };