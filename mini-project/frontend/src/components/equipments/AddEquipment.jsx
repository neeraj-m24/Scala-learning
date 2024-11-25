import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AddEquipment = () => {
  // Hardcoded fields for id and image
  const [formData, setFormData] = useState({
    deviceId: 'APPLE154MAC', // Hardcoded deviceId
    name: '', // User to fill this
    image: 'https://unsplash.com/photos/silver-macbook-on-white-table-Hin-rzhOdWs', // Hardcoded image (not shown)
    description: '', // User to fill this
    status: 'AVAILABLE', // Default status
    category: 'Laptop', // Hardcoded category
    id: 0, // Hardcoded id (not shown)

  });

  const [errors, setErrors] = useState({
    name: '',
    email: '',
    phone: '',
    service: ''
  });

  const [progress, setProgress] = useState(0);
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Handle input changes for user-editable fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const handleRadioChange = (e) => {
    setFormData({
      ...formData,
      contactMethod: e.target.value
    });
  };

  // Update progress bar based on form completion
  const updateProgress = () => {
    const inputs = document.querySelectorAll('#addEquipmentForm input, #addEquipmentForm select, #addEquipmentForm textarea');
    const filledInputs = Array.from(inputs).filter(input => {
      if (input.type === 'radio') {
        const radioGroup = document.getElementsByName(input.name);
        return Array.from(radioGroup).some(radio => radio.checked);
      }
      return input.value.trim() !== '';
    });
    const progressValue = (filledInputs.length / inputs.length) * 100;
    setProgress(progressValue);
  };

  useEffect(() => {
    updateProgress();
  }, [formData]);

  // Validate form data
  const validateForm = () => {
    let isValid = true;
    let formErrors = { ...errors };

    // Reset error messages
    for (let key in formErrors) {
      formErrors[key] = '';
    }

    // Validate required fields
    const requiredFields = ['name', 'deviceId', 'description', 'status'];
    requiredFields.forEach((field) => {
      if (!formData[field]) {
        isValid = false;
        formErrors[field] = 'This field is required.';
      }
    });

    setErrors(formErrors);
    return isValid;
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    setIsSubmitting(true);

    try {
      const response = await axios.post('http://localhost:9000/api/equipments', formData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      alert('Form submitted successfully: ' + response.data.message);

      // Reset form data after submission
      setFormData({
        deviceId: 'APPLE154MAC',
        name: '',
        image: 'https://unsplash.com/photos/silver-macbook-on-white-table-Hin-rzhOdWs',
        description: '',
        status: 'AVAILABLE',
        category: 'Laptop',
        id: 0,
      });
      setErrors({});
      setProgress(0);
    } catch (error) {
      console.error('Error submitting form:', error);
      alert('There was an error submitting your form. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center shadow-2xl p-5">
    {/* <div className="min-h-screen flex justify-center items-center bg-gradient-to-br from-pink-100 to-orange-100 p-5"> */}
      <div className="bg-white p-10 rounded-2xl shadow-xl w-full max-w-lg relative overflow-hidden">
        <div
          className="absolute top-0 left-0 h-1 bg-gradient-to-r from-pink-300 to-orange-200 transition-all duration-300"
          style={{ width: `${progress}%` }}
        ></div>
        <h1 className="text-gray-800 mb-8 text-2xl font-semibold">Add Equipment</h1>
        <form id="addEquipmentForm" onSubmit={handleSubmit}>
          {/* Hidden fields for id and image */}
          <div className="mb-6">
          <label htmlFor="deviceId" className="block mb-2 text-gray-700 text-sm font-medium">Device ID*</label>
            <input
              id="deviceId"
              name="deviceId"
              value={formData.deviceId}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            <input
              type="hidden"
              id="image"
              name="image"
              value={formData.image}
            />
            <input
              type="hidden"
              id="id"
              name="id"
              value={formData.id}
            />
          </div>

          {/* Name */}
          <div className="mb-6">
            <label htmlFor="name" className="block mb-2 text-gray-700 text-sm font-medium">Name*</label>
            <input
              type="text"
              id="name"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.name && <div className="text-pink-500 text-xs mt-1">{errors.name}</div>}
          </div>

          {/* Email */}
          <div className="mb-6">
            <label htmlFor="email" className="block mb-2 text-gray-700 text-sm font-medium">Description*</label>
            <input
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.description && <div className="text-pink-500 text-xs mt-1">{errors.description}</div>}
          </div>

          

          {/* Status */}
          <div className="mb-6">
            <label htmlFor="status" className="block mb-2 text-gray-700 text-sm font-medium">Status*</label>
            <select
              id="status"
              name="status"
              value={formData.status}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            >
              <option value="AVAILABLE">AVAILABLE</option>
              <option value="ALLOCATED">ALLOCATED</option>
            </select>
          </div>

          {/* Category */}
          <div className="mb-6">
            <label htmlFor="category" className="block mb-2 text-gray-700 text-sm font-medium">Category*</label>
            <select
              id="category"
              name="category"
              value={formData.category}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            >
              <option value="AVAILABLE">LAPTOP</option>
              <option value="AVAILABLE">MOBILE</option>
              <option value="AVAILABLE">CHARGER</option>
            </select>
          </div>

          

          {/* Submit Button */}
          <div className="mb-6">
            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full p-3 bg-gradient-to-r from-pink-300 to-orange-300 text-white font-semibold rounded-lg shadow-md transition-all hover:shadow-lg disabled:opacity-50"
            >
              {isSubmitting ? 'Submitting...' : 'Submit'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddEquipment;
