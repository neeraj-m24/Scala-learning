// import React from 'react'

// const ReturnEquipment = () => {
//   return (
//     <div>
//         <div className="min-h-screen flex items-center justify-center">
//       <div className="text-center bg-gradient-to-r from-pink-300 to-orange-300 p-8 rounded-lg shadow-xl max-w-xl w-full">
//         <h1 className="text-4xl font-bold text-gray-800 mb-4">
//           Enter Details to trigger equipment return!
//         </h1>
//         <p className="text-lg text-white mb-6">
//           Manage your equipment allocations efficiently and streamline your workforce with ease.
//         </p>
       
//       </div>
//     </div>
//     </div>
//   )
// }

// export default ReturnEquipment

import React, { useState } from 'react';
import axios from 'axios';

const ReturnEquipment = () => {
  const [formData, setFormData] = useState({
    equipmentId: '', // Equipment ID input
    status: 'AVAILABLE', // Default to AVAILABLE
  });

  const [errors, setErrors] = useState({
    equipmentId: '',
  });

  const [isSubmitting, setIsSubmitting] = useState(false);

  // Handle input changes for form fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  // Validate form data
  const validateForm = () => {
    let isValid = true;
    let formErrors = { ...errors };

    // Reset error messages
    for (let key in formErrors) {
      formErrors[key] = '';
    }

    // Validate Equipment ID to be a number
    if (!formData.equipmentId || isNaN(formData.equipmentId)) {
      isValid = false;
      formErrors.equipmentId = 'Please provide a valid equipment ID.';
    }

    setErrors(formErrors);
    return isValid;
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    setIsSubmitting(true);

    try {
      // Make the POST request to the API
      const response = await axios.post(
        `http://localhost:9000/api/allocations/return/${formData.equipmentId}/${formData.status}`,
        {},
        {
          headers: {
            'Content-Type': 'application/json',
          },
        }
      );

      alert('Equipment return status updated successfully: ' + response.data.message);

      // Reset form data after successful submission
      setFormData({
        equipmentId: '',
        status: 'AVAILABLE',
      });
      setErrors({});
    } catch (error) {
      console.error('Error submitting form:', error);
      alert('There was an error updating the equipment return status. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center p-5">
      <div className="bg-white p-10 rounded-2xl shadow-xl w-full max-w-lg">
        <h1 className="text-gray-800 mb-8 text-2xl font-semibold">Return Equipment</h1>
        <form id="returnEquipmentForm" onSubmit={handleSubmit}>
          {/* Equipment ID */}
          <div className="mb-6">
            <label htmlFor="equipmentId" className="block mb-2 text-gray-700 text-sm font-medium">Allocation ID*</label>
            <input
              type="number"
              id="equipmentId"
              name="equipmentId"
              value={formData.equipmentId}
              onChange={handleChange}
              required
              min="1"
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.equipmentId && <div className="text-pink-500 text-xs mt-1">{errors.equipmentId}</div>}
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
              <option value="MAINTENANCE">MAINTENANCE</option>
            </select>
          </div>

          {/* Submit Button */}
          <div className="mt-8 flex justify-center">
            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full p-3 bg-gradient-to-r from-pink-300 to-orange-300 text-white font-semibold rounded-lg shadow-md transition-all hover:shadow-lg disabled:opacity-50"
            >
              {isSubmitting ? 'Submitting...' : 'Update Status'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default ReturnEquipment;
