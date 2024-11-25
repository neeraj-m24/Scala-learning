import React, { useState } from 'react';
import axios from 'axios';

const AllocateEquipment = () => {
  const [formData, setFormData] = useState({
    employeeId: '', // Integer input
    employeeName: '', // User input
    employeeEmail: '', // User input
    reason: '', // User input
    equipmentId: '', // Equipment ID input
    returnDate: '', // Empty initially, user can fill it later
    status: 'ACTIVE' // Default to ACTIVE
  });

  const [errors, setErrors] = useState({
    employeeId: '',
    employeeName: '',
    employeeEmail: '',
    reason: '',
    equipmentId: '',
  });

  const [isSubmitting, setIsSubmitting] = useState(false);

  // Handle input changes for user-editable fields
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value
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

    // Validate required fields
    const requiredFields = ['employeeId', 'employeeName', 'employeeEmail', 'reason', 'equipmentId'];
    requiredFields.forEach((field) => {
      if (!formData[field]) {
        isValid = false;
        formErrors[field] = 'This field is required.';
      }
    });

    // Validate employeeId to be an integer
    if (formData.employeeId && isNaN(formData.employeeId)) {
      isValid = false;
      formErrors.employeeId = 'Employee ID must be a number.';
    }

    setErrors(formErrors);
    return isValid;
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) return;

    setIsSubmitting(true);

    // Generate dates on form submission
    const currentDate = new Date().toISOString().slice(0, 19); // Allocated date is current date
    const expectedReturnDate = new Date(new Date().setMonth(new Date().getMonth() + 1)).toISOString().slice(0, 19); // Expected return date is one month later

    // Convert employeeId and equipmentId to integers before submission
    const submissionData = {
      ...formData,
      allocatedDate: currentDate, // Set allocatedDate dynamically
      expectedReturnDate: expectedReturnDate, // Set expectedReturnDate dynamically
      employeeId: parseInt(formData.employeeId),  // Convert to integer
      equipmentId: parseInt(formData.equipmentId),  // Convert to integer
      id: 0, // Set id to 0
      returnDate: expectedReturnDate // Set returnDate same as expectedReturnDate
    };

    try {
      const response = await axios.post('http://localhost:9000/api/allocations', submissionData, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      alert('Equipment allocated successfully: ' + response.data.message);

      // Reset form data after submission
      setFormData({
        employeeId: '',
        employeeName: '',
        employeeEmail: '',
        reason: '',
        equipmentId: '',
        returnDate: '', // Return date will be set to empty initially
        status: 'ACTIVE'
      });
      setErrors({});
    } catch (error) {
      console.error('Error submitting form:', error);
      alert('There was an error allocating the equipment. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen flex justify-center items-center shadow-2xl p-5">
      <div className="bg-white p-10 rounded-2xl shadow-xl w-full max-w-lg relative overflow-hidden">
        <h1 className="text-gray-800 mb-8 text-2xl font-semibold">Allocate Equipment</h1>
        <form id="allocateEquipmentForm" onSubmit={handleSubmit}>
          {/* Employee ID */}
          <div className="mb-6">
            <label htmlFor="employeeId" className="block mb-2 text-gray-700 text-sm font-medium">Employee ID*</label>
            <input
              type="number"
              id="employeeId"
              name="employeeId"
              value={formData.employeeId}
              onChange={handleChange}
              required
              min="1"
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.employeeId && <div className="text-pink-500 text-xs mt-1">{errors.employeeId}</div>}
          </div>

          {/* Employee Name */}
          <div className="mb-6">
            <label htmlFor="employeeName" className="block mb-2 text-gray-700 text-sm font-medium">Employee Name*</label>
            <input
              type="text"
              id="employeeName"
              name="employeeName"
              value={formData.employeeName}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.employeeName && <div className="text-pink-500 text-xs mt-1">{errors.employeeName}</div>}
          </div>

          {/* Employee Email */}
          <div className="mb-6">
            <label htmlFor="employeeEmail" className="block mb-2 text-gray-700 text-sm font-medium">Employee Email*</label>
            <input
              type="email"
              id="employeeEmail"
              name="employeeEmail"
              value={formData.employeeEmail}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.employeeEmail && <div className="text-pink-500 text-xs mt-1">{errors.employeeEmail}</div>}
          </div>

          {/* Reason for Allocation */}
          <div className="mb-6">
            <label htmlFor="reason" className="block mb-2 text-gray-700 text-sm font-medium">Reason*</label>
            <input
              type="text"
              id="reason"
              name="reason"
              value={formData.reason}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.reason && <div className="text-pink-500 text-xs mt-1">{errors.reason}</div>}
          </div>

          {/* Equipment ID */}
          <div className="mb-6">
            <label htmlFor="equipmentId" className="block mb-2 text-gray-700 text-sm font-medium">Equipment ID*</label>
            <input
              type="text"
              id="equipmentId"
              name="equipmentId"
              value={formData.equipmentId}
              onChange={handleChange}
              required
              className="w-full p-3 border-2 border-gray-300 rounded-lg text-sm transition-all focus:border-pink-300 focus:ring-2 focus:ring-pink-100"
            />
            {errors.equipmentId && <div className="text-pink-500 text-xs mt-1">{errors.equipmentId}</div>}
          </div>

          

          {/* Submit Button */}
          <div className="mt-8 flex justify-center">
            <button
              type="submit"
              disabled={isSubmitting}
              // className="w-full py-3 bg-pink-500 text-white rounded-lg hover:bg-pink-600 transition-all"
              className="w-full p-3 bg-gradient-to-r from-pink-300 to-orange-300 text-white font-semibold rounded-lg shadow-md transition-all hover:shadow-lg disabled:opacity-50"
            
            >
              {isSubmitting ? 'Submitting...' : 'Allocate Equipment'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AllocateEquipment;
