// Plant Care App JavaScript



// Global variables

let currentEditingPlantId = null;

let currentFilter = 'all';

let currentSort = 'name';

let currentStatusFilter = 'all';



// API Configuration
const API_BASE_URL = 'http://localhost:8080/api';

// Plant data (will be loaded from API)
let plants = [];

// API Functions
async function fetchPlants() {
    try {
        const response = await fetch(`${API_BASE_URL}/plants`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const data = await response.json();
        plants = data;
        return data;
    } catch (error) {
        console.error('Error fetching plants:', error);
        showToast('Failed to load plants. Using offline data.', 'error');
        // Fallback to sample data if API fails
        plants = getSamplePlants();
        return plants;
    }
}

async function fetchPlantStats() {
    try {
        const response = await fetch(`${API_BASE_URL}/plants/stats`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching plant stats:', error);
        return null;
    }
}

async function fetchTotalPlants() {
    try {
        const response = await fetch(`${API_BASE_URL}/plants/total-plants`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error fetching total plants:', error);
        return 0;
    }
}

async function createPlant(plantData) {
    try {
        const response = await fetch(`${API_BASE_URL}/plants`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(plantData)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error creating plant:', error);
        showToast('Failed to create plant. Please try again.', 'error');
        throw error;
    }
}

async function updatePlant(plantId, plantData) {
    try {
        const response = await fetch(`${API_BASE_URL}/plants/${plantId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(plantData)
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error updating plant:', error);
        showToast('Failed to update plant. Please try again.', 'error');
        throw error;
    }
}

async function deletePlantAPI(plantId) {
    try {
        const response = await fetch(`${API_BASE_URL}/plants/${plantId}`, {
            method: 'DELETE'
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return true;
    } catch (error) {
        console.error('Error deleting plant:', error);
        showToast('Failed to delete plant. Please try again.', 'error');
        throw error;
    }
}

async function waterPlantAPI(plantId) {
    try {
        const response = await fetch(`${API_BASE_URL}/plants/${plantId}/water`, {
            method: 'POST'
        });
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return await response.json();
    } catch (error) {
        console.error('Error watering plant:', error);
        showToast('Failed to water plant. Please try again.', 'error');
        throw error;
    }
}

async function generatePlantPdf(plantId) {
    try {
        showToast('Generating PDF care guide...', 'success');
        
        const response = await fetch(`${API_BASE_URL}/pdf/plant/${plantId}`, {
            method: 'GET'
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        // Get the plant name for the filename
        const plant = plants.find(p => p.id === plantId);
        const plantName = plant ? plant.name.replace(/[^a-zA-Z0-9]/g, '_') : 'plant';
        
        // Create blob and download
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `${plantName}_Care_Guide.html`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
        
        showToast('PDF care guide downloaded successfully!', 'success');
    } catch (error) {
        console.error('Error generating PDF:', error);
        showToast('Failed to generate PDF. Please try again.', 'error');
    }
}

// Sample data for fallback
function getSamplePlants() {
    return [
    {

        id: 1,

        name: "Fiddle Leaf Fig",

        type: "tropical",

            imageUrl: "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=400&h=300&fit=crop&crop=center",
        wateringFrequency: "weekly",

        sunlightNeeds: "medium",

        lastWatered: "2024-01-15",

        nextWatering: "2024-01-22",

            careNotes: "Keep away from direct sunlight. Water when top inch of soil is dry.",
        addedDate: "2024-01-01"

    },

    {

        id: 2,

        name: "Snake Plant",

        type: "succulent",

            imageUrl: "https://images.unsplash.com/photo-1593482892296-61358a3a5b1e?w=400&h=300&fit=crop&crop=center",
        wateringFrequency: "every-2-weeks",

        sunlightNeeds: "low",

        lastWatered: "2024-01-10",

        nextWatering: "2024-01-24",

            careNotes: "Very low maintenance. Can survive in low light conditions.",
        addedDate: "2024-01-05"

        }
    ];
}



let notifications = [

    {

        id: 1,

        type: "watering",

        plantName: "Fiddle Leaf Fig",

        message: "Time to water your Fiddle Leaf Fig!",

        time: "2 hours ago",

        urgent: true

    },

    {

        id: 2,

        type: "sunlight",

        plantName: "Aloe Vera",

        message: "Your Aloe Vera needs more sunlight today.",

        time: "1 day ago",

        urgent: false

    },

    {

        id: 3,

        type: "repotting",

        plantName: "Monstera Deliciosa",

        message: "Consider repotting your Monstera - it's getting big!",

        time: "3 days ago",

        urgent: false

    }

];



// DOM Elements

const plantsGrid = document.getElementById('plants-grid');

const plantDetailsContent = document.getElementById('plant-details-content');

const notificationsList = document.getElementById('notifications-list');

const addPlantForm = document.getElementById('add-plant-form');

const toast = document.getElementById('toast');



// Initialize the app

document.addEventListener('DOMContentLoaded', async function() {
    // Initialize navigation
    initializeNavigation();
    
    await loadDashboard();
    loadNotifications();

    setupEventListeners();

    setupAccordion();

    updateCategoryCounts();

});



// Navigation functions

function showSection(sectionId) {

    // Hide all sections

    document.querySelectorAll('.section').forEach(section => {

        section.classList.remove('active');

    });

    

    // Show selected section

    document.getElementById(sectionId).classList.add('active');

    

    // Update navigation

    document.querySelectorAll('.nav-link').forEach(link => {

        link.classList.remove('active');

    });

    

    // Update active nav link

    const activeLink = document.querySelector(`[href="#${sectionId}"]`);

    if (activeLink) {

        activeLink.classList.add('active');

    }

    // Load section-specific content

    loadSectionContent(sectionId);

}

// Initialize navigation click handlers

function initializeNavigation() {

    // Add click handlers to navigation links

    document.querySelectorAll('.nav-link').forEach(link => {

        link.addEventListener('click', function(e) {

            e.preventDefault();

            const sectionId = this.getAttribute('href').substring(1); // Remove the # from href

            showSection(sectionId);

        });

    });

}

// Load section-specific content

function loadSectionContent(sectionId) {

    if (sectionId === 'dashboard') {

        loadDashboard();

    } else if (sectionId === 'plants') {

        loadPlantsSection();

    } else if (sectionId === 'notifications') {

        loadNotifications();

    }

}

// Load plants section
async function loadPlantsSection() {
    const plantsGrid = document.getElementById('plants-grid');
    if (!plantsGrid) return;

    try {
        plantsGrid.innerHTML = '<div class="loading">Loading plants...</div>';
        
        // Fetch plants from API
        const response = await fetch(`${API_BASE_URL}/plants`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const plants = await response.json();
        
        if (plants.length === 0) {
            plantsGrid.innerHTML = `
                <div class="empty-state">
                    <i class="fas fa-seedling"></i>
                    <h3>No plants yet</h3>
                    <p>Start your plant collection by adding your first plant!</p>
                    <button class="btn btn-primary" onclick="showSection('add-plant')">
                        <i class="fas fa-plus"></i>
                        Add Your First Plant
                    </button>
                </div>
            `;
            return;
        }

        // Display plants in grid
        plantsGrid.innerHTML = plants.map(plant => createPlantCard(plant)).join('');
        
    } catch (error) {
        console.error('Error loading plants:', error);
        plantsGrid.innerHTML = `
            <div class="error-state">
                <i class="fas fa-exclamation-triangle"></i>
                <h3>Failed to load plants</h3>
                <p>Please try again later.</p>
                <button class="btn btn-secondary" onclick="loadPlantsSection()">
                    <i class="fas fa-refresh"></i>
                    Retry
                </button>
            </div>
        `;
    }
}

// Dashboard functions

async function loadDashboard() {
    if (!plantsGrid) return;

    

    plantsGrid.innerHTML = '<div class="loading">Loading plants...</div>';
    
    try {
        // Load plants from API
        await fetchPlants();
    

    if (plants.length === 0) {

        plantsGrid.innerHTML = `

            <div class="empty-state">

                <i class="fas fa-seedling" style="font-size: 4rem; color: var(--accent-green); margin-bottom: 1rem;"></i>

                <h3>No plants yet</h3>

                <p>Start your plant collection by adding your first plant!</p>

                <button class="btn btn-primary" onclick="showSection('add-plant')">

                    <i class="fas fa-plus"></i>

                    Add Your First Plant

                </button>

            </div>

        `;

        return;

    }

    

    // Filter and sort plants

    let filteredPlants = filterPlantsByCategory(plants, currentFilter);

    filteredPlants = filterPlantsByStatus(filteredPlants, currentStatusFilter);

    filteredPlants = sortPlantsArray(filteredPlants, currentSort);

    

        // Display plants
        plantsGrid.innerHTML = '';
    filteredPlants.forEach(plant => {

        const plantCard = createPlantCard(plant);

        plantsGrid.appendChild(plantCard);

    });

        
        // Update stats
        await updateStats();
        
    } catch (error) {
        console.error('Error loading dashboard:', error);
        plantsGrid.innerHTML = `
            <div class="error-state">
                <i class="fas fa-exclamation-triangle" style="font-size: 4rem; color: var(--accent-red); margin-bottom: 1rem;"></i>
                <h3>Failed to load plants</h3>
                <p>Please check your connection and try again.</p>
                <button class="btn btn-primary" onclick="loadDashboard()">
                    <i class="fas fa-refresh"></i>
                    Retry
                </button>
            </div>
        `;
    }
}

// Update stats from API
async function updateStats() {
    try {
        // Fetch both total plants count and general stats
        const [totalPlantsResponse, statsResponse] = await Promise.all([
            fetch(`${API_BASE_URL}/plants/total-plants`),
            fetch(`${API_BASE_URL}/plants/stats`)
        ]);

        // Update total plants count
        if (totalPlantsResponse.ok) {
            const totalPlants = await totalPlantsResponse.json();
            const totalPlantsElement = document.getElementById('total-plants-count');
            if (totalPlantsElement) {
                totalPlantsElement.textContent = totalPlants;
            }
        }

        // Update other stats
        if (statsResponse.ok) {
            const stats = await statsResponse.json();
            console.log('Plant stats:', stats);
            
            // Update need watering count
            const needWateringElement = document.getElementById('need-watering-count');
            if (needWateringElement && stats.plantsNeedingWatering !== undefined) {
                needWateringElement.textContent = stats.plantsNeedingWatering;
            }

            // For now, set need sunlight and due for repotting to 0
            // You can add specific endpoints for these later
            const needSunlightElement = document.getElementById('need-sunlight-count');
            if (needSunlightElement) {
                needSunlightElement.textContent = '0';
            }

            const dueRepottingElement = document.getElementById('due-repotting-count');
            if (dueRepottingElement) {
                dueRepottingElement.textContent = '0';
            }
        }
    } catch (error) {
        console.error('Error loading stats:', error);
        // Set default values on error
        document.getElementById('total-plants-count').textContent = '0';
        document.getElementById('need-watering-count').textContent = '0';
        document.getElementById('need-sunlight-count').textContent = '0';
        document.getElementById('due-repotting-count').textContent = '0';
    }
}



function createPlantCard(plant) {

    const card = document.createElement('div');

    card.className = 'plant-card';

    card.onclick = () => showPlantDetails(plant.id);

    

    const needsWatering = isWateringDue(plant.nextWatering);

    const needsSunlight = isSunlightNeeded(plant.sunlightNeeds);

    

    card.innerHTML = `

        <div class="plant-image">

            ${plant.imageUrl ? 
                `<img src="${plant.imageUrl}" alt="${plant.name}" onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                 <i class="fas fa-seedling" style="display: none;"></i>` : 

                `<i class="fas fa-seedling"></i>`

            }

            ${needsWatering ? '<div class="urgent-badge" style="position: absolute; top: 10px; right: 10px; background: #e74c3c; color: white; padding: 4px 8px; border-radius: 12px; font-size: 0.8rem; font-weight: 600;">Water!</div>' : ''}

        </div>

        <div class="plant-card-content">

            <h3 class="plant-name">${plant.name}</h3>

            <p class="plant-type">${plant.type}</p>

            <div class="plant-care-info">

                <div class="care-item">

                    <i class="fas fa-tint"></i>

                    <span>${formatWateringFrequency(plant.wateringFrequency)}</span>

                </div>

                <div class="care-item">

                    <i class="fas fa-sun"></i>

                    <span>${formatSunlightNeeds(plant.sunlightNeeds)}</span>

                </div>

            </div>

            <div class="plant-actions">

                <button class="btn btn-sm btn-primary" onclick="event.stopPropagation(); waterPlant(${plant.id})">

                    <i class="fas fa-tint"></i>

                    Water

                </button>

                <button class="btn btn-sm btn-secondary" onclick="event.stopPropagation(); showPlantDetails(${plant.id})">

                    <i class="fas fa-eye"></i>

                    View

                </button>

                <button class="btn btn-sm btn-secondary" onclick="event.stopPropagation(); editPlant(${plant.id})">

                    <i class="fas fa-edit"></i>

                    Edit

                </button>

                <button class="btn btn-sm btn-info" onclick="event.stopPropagation(); generatePlantPdf(${plant.id})" title="Generate PDF Care Guide">

                    <i class="fas fa-file-pdf"></i>

                    PDF

                </button>

            </div>

        </div>

    `;

    

    return card;

}



// Plant details functions

function showPlantDetails(plantId) {

    const plant = plants.find(p => p.id === plantId);

    if (!plant) return;

    

    showSection('plant-details');

    

    plantDetailsContent.innerHTML = `

        <div class="plant-details-header">

            <div class="plant-details-image">

                ${plant.imageUrl ? 
                    `<img src="${plant.imageUrl}" alt="${plant.name}" onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                     <i class="fas fa-seedling" style="display: none;"></i>` : 

                    `<i class="fas fa-seedling"></i>`

                }

            </div>

            <h1 class="plant-details-name">${plant.name}</h1>

            <p class="plant-details-type">${plant.type}</p>

        </div>

        <div class="plant-details-info">

            <div class="care-schedule">

                <div class="care-schedule-item">

                    <h4><i class="fas fa-tint"></i> Watering</h4>

                    <p>${formatWateringFrequency(plant.wateringFrequency)}</p>

                    <p><strong>Last watered:</strong> ${formatDate(plant.lastWatered)}</p>

                    <p><strong>Next watering:</strong> ${formatDate(plant.nextWatering)}</p>

                </div>

                <div class="care-schedule-item">

                    <h4><i class="fas fa-sun"></i> Sunlight</h4>

                    <p>${formatSunlightNeeds(plant.sunlightNeeds)}</p>

                </div>

                <div class="care-schedule-item">

                    <h4><i class="fas fa-calendar"></i> Added</h4>

                    <p>${formatDate(plant.addedDate)}</p>

                </div>

            </div>

            ${plant.careNotes ? `
                <div class="plant-notes">

                    <h4>Care Notes</h4>

                    <p>${plant.careNotes}</p>
                </div>

            ` : ''}

            <div class="plant-actions" style="margin-top: 2rem; display: flex; gap: 1rem; justify-content: center;">

                <button class="btn btn-primary" onclick="waterPlant(${plant.id})">

                    <i class="fas fa-tint"></i>

                    Mark as Watered

                </button>

                <button class="btn btn-secondary" onclick="editPlant(${plant.id})">

                    <i class="fas fa-edit"></i>

                    Edit Plant

                </button>

                <button class="btn btn-info" onclick="generatePlantPdf(${plant.id})" title="Generate PDF Care Guide">

                    <i class="fas fa-file-pdf"></i>

                    Generate PDF

                </button>

                <button class="btn btn-secondary" onclick="deletePlant(${plant.id})" style="background: #e74c3c; color: white;">

                    <i class="fas fa-trash"></i>

                    Delete Plant

                </button>

            </div>

        </div>

    `;

}



// Add plant functions

function setupEventListeners() {

    // Add plant form submission

    if (addPlantForm) {

        addPlantForm.addEventListener('submit', handleAddPlant);

    }

    

    // Edit plant form submission

    const editPlantForm = document.getElementById('edit-plant-form');

    if (editPlantForm) {

        editPlantForm.addEventListener('submit', handleEditPlant);

    }

    

    // Photo upload

    const photoInput = document.getElementById('plant-photo');

    if (photoInput) {

        photoInput.addEventListener('change', handlePhotoUpload);

    }

    

    // Edit photo upload

    const editPhotoInput = document.getElementById('edit-plant-photo');

    if (editPhotoInput) {

        editPhotoInput.addEventListener('change', handleEditPhotoUpload);

    }

    

    // Close modal when clicking outside

    const modal = document.getElementById('edit-plant-modal');

    if (modal) {

        modal.addEventListener('click', function(e) {

            if (e.target === modal) {

                closeEditModal();

            }

        });

    }

}



function handleAddPlant(e) {

    e.preventDefault();

    console.log('Form submitted!');
    

    const plantData = {

        name: document.getElementById('plant-name').value,

        type: document.getElementById('plant-type').value,

        wateringFrequency: document.getElementById('watering-frequency').value,

        sunlightNeeds: document.getElementById('sunlight-needs').value,

        careNotes: document.getElementById('plant-notes').value,
        imageUrl: null
    };
    
    console.log('Plant data:', plantData);
    
    // Validate required fields
    if (!plantData.name || !plantData.type || !plantData.wateringFrequency || !plantData.sunlightNeeds) {
        showToast('Please fill in all required fields', 'error');
        return;
    }
    

    // Handle photo upload

    const photoFile = document.getElementById('plant-photo').files[0];

    if (photoFile) {

        console.log('Photo file found, processing...');
        const reader = new FileReader();

        reader.onload = function(e) {

            plantData.imageUrl = e.target.result;
            console.log('Photo processed, calling addPlant...');
            addPlant(plantData);

        };

        reader.readAsDataURL(photoFile);

    } else {

        console.log('No photo file, calling addPlant directly...');
        addPlant(plantData);

    }

}



async function addPlant(plantData) {
    try {
        console.log('addPlant called with:', plantData);
        console.log('Calling createPlant API...');
        const newPlant = await createPlant(plantData);
        console.log('API response:', newPlant);
        plants.push(newPlant);
        showToast('Plant added successfully!', 'success');

        addPlantForm.reset();

        removePhoto();

        updateCategoryCounts();

        showSection('dashboard');

        await loadDashboard();
    } catch (error) {
        console.error('Error adding plant:', error);
        showToast('Failed to add plant. Please try again.', 'error');
    }
}



function handlePhotoUpload(e) {

    const file = e.target.files[0];

    if (file) {

        const reader = new FileReader();

        reader.onload = function(e) {

            const preview = document.getElementById('photo-preview');

            const previewImg = document.getElementById('preview-img');

            previewImg.src = e.target.result;

            preview.style.display = 'block';

        };

        reader.readAsDataURL(file);

    }

}



function removePhoto() {

    const preview = document.getElementById('photo-preview');

    const input = document.getElementById('plant-photo');

    preview.style.display = 'none';

    input.value = '';

}



// Edit plant modal functions

function openEditModal(plant) {

    const modal = document.getElementById('edit-plant-modal');

    const form = document.getElementById('edit-plant-form');

    

    // Populate form with current plant data

    document.getElementById('edit-plant-name').value = plant.name;

    document.getElementById('edit-plant-type').value = plant.type;

    document.getElementById('edit-watering-frequency').value = plant.wateringFrequency;

    document.getElementById('edit-sunlight-needs').value = plant.sunlightNeeds;

    document.getElementById('edit-plant-notes').value = plant.careNotes || '';

    

    // Handle existing photo

    const photoPreview = document.getElementById('edit-photo-preview');

    const previewImg = document.getElementById('edit-preview-img');

    const uploadArea = photoPreview.previousElementSibling;

    

    if (plant.imageUrl) {

        previewImg.src = plant.imageUrl;

        photoPreview.style.display = 'block';

        uploadArea.style.display = 'none';

    } else {

        photoPreview.style.display = 'none';

        uploadArea.style.display = 'block';

    }

    

    // Show modal

    modal.classList.add('show');

    document.body.style.overflow = 'hidden';

}



function closeEditModal() {

    const modal = document.getElementById('edit-plant-modal');

    modal.classList.remove('show');

    document.body.style.overflow = 'auto';

    

    // Reset form

    const form = document.getElementById('edit-plant-form');

    form.reset();

    removeEditPhoto();

    

    currentEditingPlantId = null;

}



async function handleEditPlant(e) {

    e.preventDefault();

    

    const plant = plants.find(p => p.id === currentEditingPlantId);

    if (!plant) return;

    

    // Update plant data

    plant.name = document.getElementById('edit-plant-name').value;

    plant.type = document.getElementById('edit-plant-type').value;

    plant.wateringFrequency = document.getElementById('edit-watering-frequency').value;

    plant.sunlightNeeds = document.getElementById('edit-sunlight-needs').value;

    plant.careNotes = document.getElementById('edit-plant-notes').value;

    

    // Handle photo update

    const photoFile = document.getElementById('edit-plant-photo').files[0];

    if (photoFile) {

        const reader = new FileReader();

        reader.onload = async function(e) {

            plant.imageUrl = e.target.result;

            // Send update to backend

            try {
                const updatedPlant = await updatePlant(plant.id, plant);
                // Update the plant in the local array with the response from backend
                const index = plants.findIndex(p => p.id === plant.id);
                if (index !== -1) {
                    plants[index] = updatedPlant;
                }
                updatePlantDisplay();
                closeEditModal();
                showToast('Plant updated successfully!', 'success');
            } catch (error) {
                console.error('Error updating plant:', error);
                showToast('Failed to update plant. Please try again.', 'error');
            }

        };

        reader.readAsDataURL(photoFile);

    } else {

        // Send update to backend even without photo change

        try {
            const updatedPlant = await updatePlant(plant.id, plant);
            // Update the plant in the local array with the response from backend
            const index = plants.findIndex(p => p.id === plant.id);
            if (index !== -1) {
                plants[index] = updatedPlant;
            }
            updatePlantDisplay();
            closeEditModal();
            showToast('Plant updated successfully!', 'success');
        } catch (error) {
            console.error('Error updating plant:', error);
            showToast('Failed to update plant. Please try again.', 'error');
        }

    }

}



function handleEditPhotoUpload(e) {

    const file = e.target.files[0];

    if (file) {

        const reader = new FileReader();

        reader.onload = function(e) {

            const preview = document.getElementById('edit-photo-preview');

            const previewImg = document.getElementById('edit-preview-img');

            const uploadArea = preview.previousElementSibling;

            

            previewImg.src = e.target.result;

            preview.style.display = 'block';

            uploadArea.style.display = 'none';

        };

        reader.readAsDataURL(file);

    }

}



function removeEditPhoto() {

    const preview = document.getElementById('edit-photo-preview');

    const input = document.getElementById('edit-plant-photo');

    const uploadArea = preview.previousElementSibling;

    

    preview.style.display = 'none';

    uploadArea.style.display = 'block';

    input.value = '';

}



function updatePlantDisplay() {

    // Update dashboard

    loadDashboard();

    

    // Update plant details if currently viewing the edited plant

    const currentSection = document.querySelector('.section.active');

    if (currentSection && currentSection.id === 'plant-details') {

        showPlantDetails(currentEditingPlantId);

    }

}



// Plant actions

async function waterPlant(plantId) {
    try {
        const updatedPlant = await waterPlantAPI(plantId);
    const plant = plants.find(p => p.id === plantId);

    if (plant) {

            Object.assign(plant, updatedPlant);
        }
        showToast(`${plant?.name || 'Plant'} has been watered!`, 'success');
        await loadDashboard();
    } catch (error) {
        console.error('Error watering plant:', error);
        showToast('Failed to water plant. Please try again.', 'error');
    }

}



function editPlant(plantId) {

    const plant = plants.find(p => p.id === plantId);

    if (!plant) return;

    

    currentEditingPlantId = plantId;

    openEditModal(plant);

}



async function deletePlant(plantId) {
    if (confirm('Are you sure you want to delete this plant?')) {

        try {
            await deletePlantAPI(plantId);
        plants = plants.filter(p => p.id !== plantId);

        showToast('Plant deleted successfully!', 'success');

        updateCategoryCounts();

        showSection('dashboard');

            await loadDashboard();
        } catch (error) {
            console.error('Error deleting plant:', error);
            showToast('Failed to delete plant. Please try again.', 'error');
        }
    }

}



// Notifications

function loadNotifications() {

    if (!notificationsList) return;

    

    notificationsList.innerHTML = '';

    

    if (notifications.length === 0) {

        notificationsList.innerHTML = `

            <div class="empty-state">

                <i class="fas fa-bell-slash" style="font-size: 3rem; color: var(--accent-green); margin-bottom: 1rem;"></i>

                <h3>No notifications</h3>

                <p>You're all caught up! Your plants are happy and healthy.</p>

            </div>

        `;

        return;

    }

    

    notifications.forEach(notification => {

        const notificationElement = createNotificationElement(notification);

        notificationsList.appendChild(notificationElement);

    });

}



function createNotificationElement(notification) {

    const element = document.createElement('div');

    element.className = 'notification-item';

    

    const iconClass = notification.type === 'watering' ? 'fas fa-tint' : 

                     notification.type === 'sunlight' ? 'fas fa-sun' : 

                     'fas fa-seedling';

    

    element.innerHTML = `

        <div class="notification-icon ${notification.type}">

            <i class="${iconClass}"></i>

        </div>

        <div class="notification-content">

            <h4>${notification.plantName}</h4>

            <p>${notification.message}</p>

        </div>

        <div class="notification-time">${notification.time}</div>

    `;

    

    return element;

}



// Utility functions

function formatWateringFrequency(frequency) {

    const frequencyMap = {

        'daily': 'Daily',

        'every-2-days': 'Every 2 days',

        'weekly': 'Weekly',

        'every-2-weeks': 'Every 2 weeks',

        'monthly': 'Monthly'

    };

    return frequencyMap[frequency] || frequency;

}



function formatSunlightNeeds(needs) {

    const needsMap = {

        'low': 'Low light',

        'medium': 'Medium light',

        'high': 'High light'

    };

    return needsMap[needs] || needs;

}



function formatDate(dateString) {

    const date = new Date(dateString);

    return date.toLocaleDateString('en-US', { 

        year: 'numeric', 

        month: 'long', 

        day: 'numeric' 

    });

}



function calculateNextWatering(frequency) {

    const today = new Date();

    const daysToAdd = {

        'daily': 1,

        'every-2-days': 2,

        'weekly': 7,

        'every-2-weeks': 14,

        'monthly': 30

    };

    

    const days = daysToAdd[frequency] || 7;

    const nextDate = new Date(today);

    nextDate.setDate(today.getDate() + days);

    

    return nextDate.toISOString().split('T')[0];

}



function isWateringDue(nextWatering) {

    const today = new Date();

    const nextDate = new Date(nextWatering);

    return nextDate <= today;

}



function isSunlightNeeded(sunlightNeeds) {

    // Simple logic - could be enhanced with actual sunlight tracking

    return sunlightNeeds === 'high';

}



function showToast(message, type = 'success') {

    const toastElement = document.getElementById('toast');

    const toastIcon = toastElement.querySelector('.toast-icon');

    const toastMessage = toastElement.querySelector('.toast-message');

    

    toastIcon.className = `toast-icon ${type}`;

    toastIcon.innerHTML = type === 'success' ? '✓' : '✕';

    toastMessage.textContent = message;

    

    toastElement.classList.add('show');

    

    setTimeout(() => {

        toastElement.classList.remove('show');

    }, 3000);

}



// Mobile navigation toggle

function toggleMobileNav() {

    const navMenu = document.querySelector('.nav-menu');

    navMenu.classList.toggle('active');

}



// Add mobile nav toggle event listener

document.addEventListener('DOMContentLoaded', function() {

    const navToggle = document.querySelector('.nav-toggle');

    if (navToggle) {

        navToggle.addEventListener('click', toggleMobileNav);

    }

    

    // Add keyboard support for modal

    document.addEventListener('keydown', function(e) {

        if (e.key === 'Escape') {

            closeEditModal();

        }

    });

});



// Accordion functionality

function setupAccordion() {

    const toggle = document.getElementById('categories-toggle');

    const content = document.getElementById('categories-content');

    

    if (toggle && content) {

        toggle.addEventListener('click', function() {

            content.classList.toggle('active');

            toggle.classList.toggle('active');

        });

    }

}



// Filtering and sorting functions

function filterPlants(category) {

    currentFilter = category;

    

    // Update active button

    document.querySelectorAll('.category-btn').forEach(btn => {

        btn.classList.remove('active');

    });

    document.querySelector(`[data-category="${category}"]`).classList.add('active');

    

    loadDashboard();

}



function filterByStatus() {

    const statusFilter = document.getElementById('status-filter');

    currentStatusFilter = statusFilter.value;

    loadDashboard();

}



function sortPlants() {

    const sortSelect = document.getElementById('sort-select');

    currentSort = sortSelect.value;

    loadDashboard();

}



function filterPlantsByCategory(plantsArray, category) {

    if (category === 'all') {

        return plantsArray;

    }

    return plantsArray.filter(plant => plant.type === category);

}



function filterPlantsByStatus(plantsArray, status) {

    if (status === 'all') {

        return plantsArray;

    }

    

    return plantsArray.filter(plant => {

        switch (status) {

            case 'needs-watering':

                return isWateringDue(plant.nextWatering);

            case 'needs-sunlight':

                return isSunlightNeeded(plant.sunlightNeeds);

            case 'healthy':

                return !isWateringDue(plant.nextWatering) && !isSunlightNeeded(plant.sunlightNeeds);

            default:

                return true;

        }

    });

}



function sortPlantsArray(plantsArray, sortBy) {

    return [...plantsArray].sort((a, b) => {

        switch (sortBy) {

            case 'name':

                return a.name.localeCompare(b.name);

            case 'name-desc':

                return b.name.localeCompare(a.name);

            case 'type':

                return a.type.localeCompare(b.type);

            case 'added-date':

                return new Date(b.addedDate) - new Date(a.addedDate);

            case 'watering-due':

                return new Date(a.nextWatering) - new Date(b.nextWatering);

            default:

                return 0;

        }

    });

}



function updateCategoryCounts() {

    const categories = ['all', 'tropical', 'succulent', 'flowering', 'herb', 'fern', 'cactus'];

    

    categories.forEach(category => {

        const countElement = document.getElementById(`count-${category}`);

        if (countElement) {

            let count;

            if (category === 'all') {

                count = plants.length;

            } else {

                count = plants.filter(plant => plant.type === category).length;

            }

            countElement.textContent = count;

        }

    });

}




function updatePlantDisplay() {

    // Update dashboard

    loadDashboard();

    

    // Update plant details if currently viewing the edited plant

    const currentSection = document.querySelector('.section.active');

    if (currentSection && currentSection.id === 'plant-details') {

        showPlantDetails(currentEditingPlantId);

    }

}



// Plant actions

async function waterPlant(plantId) {
    try {
        const updatedPlant = await waterPlantAPI(plantId);
    const plant = plants.find(p => p.id === plantId);

    if (plant) {

            Object.assign(plant, updatedPlant);
        }
        showToast(`${plant?.name || 'Plant'} has been watered!`, 'success');
        await loadDashboard();
    } catch (error) {
        console.error('Error watering plant:', error);
        showToast('Failed to water plant. Please try again.', 'error');
    }

}



function editPlant(plantId) {

    const plant = plants.find(p => p.id === plantId);

    if (!plant) return;

    

    currentEditingPlantId = plantId;

    openEditModal(plant);

}



async function deletePlant(plantId) {
    if (confirm('Are you sure you want to delete this plant?')) {

        try {
            await deletePlantAPI(plantId);
        plants = plants.filter(p => p.id !== plantId);

        showToast('Plant deleted successfully!', 'success');

        updateCategoryCounts();

        showSection('dashboard');

            await loadDashboard();
        } catch (error) {
            console.error('Error deleting plant:', error);
            showToast('Failed to delete plant. Please try again.', 'error');
        }
    }

}



// Notifications

function loadNotifications() {

    if (!notificationsList) return;

    

    notificationsList.innerHTML = '';

    

    if (notifications.length === 0) {

        notificationsList.innerHTML = `

            <div class="empty-state">

                <i class="fas fa-bell-slash" style="font-size: 3rem; color: var(--accent-green); margin-bottom: 1rem;"></i>

                <h3>No notifications</h3>

                <p>You're all caught up! Your plants are happy and healthy.</p>

            </div>

        `;

        return;

    }

    

    notifications.forEach(notification => {

        const notificationElement = createNotificationElement(notification);

        notificationsList.appendChild(notificationElement);

    });

}



function createNotificationElement(notification) {

    const element = document.createElement('div');

    element.className = 'notification-item';

    

    const iconClass = notification.type === 'watering' ? 'fas fa-tint' : 

                     notification.type === 'sunlight' ? 'fas fa-sun' : 

                     'fas fa-seedling';

    

    element.innerHTML = `

        <div class="notification-icon ${notification.type}">

            <i class="${iconClass}"></i>

        </div>

        <div class="notification-content">

            <h4>${notification.plantName}</h4>

            <p>${notification.message}</p>

        </div>

        <div class="notification-time">${notification.time}</div>

    `;

    

    return element;

}



// Utility functions

function formatWateringFrequency(frequency) {

    const frequencyMap = {

        'daily': 'Daily',

        'every-2-days': 'Every 2 days',

        'weekly': 'Weekly',

        'every-2-weeks': 'Every 2 weeks',

        'monthly': 'Monthly'

    };

    return frequencyMap[frequency] || frequency;

}



function formatSunlightNeeds(needs) {

    const needsMap = {

        'low': 'Low light',

        'medium': 'Medium light',

        'high': 'High light'

    };

    return needsMap[needs] || needs;

}



function formatDate(dateString) {

    const date = new Date(dateString);

    return date.toLocaleDateString('en-US', { 

        year: 'numeric', 

        month: 'long', 

        day: 'numeric' 

    });

}



function calculateNextWatering(frequency) {

    const today = new Date();

    const daysToAdd = {

        'daily': 1,

        'every-2-days': 2,

        'weekly': 7,

        'every-2-weeks': 14,

        'monthly': 30

    };

    

    const days = daysToAdd[frequency] || 7;

    const nextDate = new Date(today);

    nextDate.setDate(today.getDate() + days);

    

    return nextDate.toISOString().split('T')[0];

}



function isWateringDue(nextWatering) {

    const today = new Date();

    const nextDate = new Date(nextWatering);

    return nextDate <= today;

}



function isSunlightNeeded(sunlightNeeds) {

    // Simple logic - could be enhanced with actual sunlight tracking

    return sunlightNeeds === 'high';

}



function showToast(message, type = 'success') {

    const toastElement = document.getElementById('toast');

    const toastIcon = toastElement.querySelector('.toast-icon');

    const toastMessage = toastElement.querySelector('.toast-message');

    

    toastIcon.className = `toast-icon ${type}`;

    toastIcon.innerHTML = type === 'success' ? '✓' : '✕';

    toastMessage.textContent = message;

    

    toastElement.classList.add('show');

    

    setTimeout(() => {

        toastElement.classList.remove('show');

    }, 3000);

}



// Mobile navigation toggle

function toggleMobileNav() {

    const navMenu = document.querySelector('.nav-menu');

    navMenu.classList.toggle('active');

}



// Add mobile nav toggle event listener

document.addEventListener('DOMContentLoaded', function() {

    const navToggle = document.querySelector('.nav-toggle');

    if (navToggle) {

        navToggle.addEventListener('click', toggleMobileNav);

    }

    

    // Add keyboard support for modal

    document.addEventListener('keydown', function(e) {

        if (e.key === 'Escape') {

            closeEditModal();

        }

    });

});



// Accordion functionality

function setupAccordion() {

    const toggle = document.getElementById('categories-toggle');

    const content = document.getElementById('categories-content');

    

    if (toggle && content) {

        toggle.addEventListener('click', function() {

            content.classList.toggle('active');

            toggle.classList.toggle('active');

        });

    }

}



// Filtering and sorting functions

function filterPlants(category) {

    currentFilter = category;

    

    // Update active button

    document.querySelectorAll('.category-btn').forEach(btn => {

        btn.classList.remove('active');

    });

    document.querySelector(`[data-category="${category}"]`).classList.add('active');

    

    loadDashboard();

}



function filterByStatus() {

    const statusFilter = document.getElementById('status-filter');

    currentStatusFilter = statusFilter.value;

    loadDashboard();

}



function sortPlants() {

    const sortSelect = document.getElementById('sort-select');

    currentSort = sortSelect.value;

    loadDashboard();

}



function filterPlantsByCategory(plantsArray, category) {

    if (category === 'all') {

        return plantsArray;

    }

    return plantsArray.filter(plant => plant.type === category);

}



function filterPlantsByStatus(plantsArray, status) {

    if (status === 'all') {

        return plantsArray;

    }

    

    return plantsArray.filter(plant => {

        switch (status) {

            case 'needs-watering':

                return isWateringDue(plant.nextWatering);

            case 'needs-sunlight':

                return isSunlightNeeded(plant.sunlightNeeds);

            case 'healthy':

                return !isWateringDue(plant.nextWatering) && !isSunlightNeeded(plant.sunlightNeeds);

            default:

                return true;

        }

    });

}



function sortPlantsArray(plantsArray, sortBy) {

    return [...plantsArray].sort((a, b) => {

        switch (sortBy) {

            case 'name':

                return a.name.localeCompare(b.name);

            case 'name-desc':

                return b.name.localeCompare(a.name);

            case 'type':

                return a.type.localeCompare(b.type);

            case 'added-date':

                return new Date(b.addedDate) - new Date(a.addedDate);

            case 'watering-due':

                return new Date(a.nextWatering) - new Date(b.nextWatering);

            default:

                return 0;

        }

    });

}



function updateCategoryCounts() {

    const categories = ['all', 'tropical', 'succulent', 'flowering', 'herb', 'fern', 'cactus'];

    

    categories.forEach(category => {

        const countElement = document.getElementById(`count-${category}`);

        if (countElement) {

            let count;

            if (category === 'all') {

                count = plants.length;

            } else {

                count = plants.filter(plant => plant.type === category).length;

            }

            countElement.textContent = count;

        }

    });

}


