import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ViewAllocations = () => {
  // State to store fetched allocation data
  const [allocations, setAllocations] = useState([]);
  const [loading, setLoading] = useState(true);  // For showing loading state
  const [error, setError] = useState(null); // To handle errors

  // Fetch the data from the API when the component mounts
  useEffect(() => {
    const fetchAllocations = async () => {
      try {
        // Replace with the correct API endpoint for allocations
        const response = await axios.get('http://localhost:9000/api/allocations');
        setAllocations(response.data);  // Store fetched data in state
        setLoading(false);  // Set loading to false once data is fetched
      } catch (err) {
        setError('Failed to fetch allocations');
        setLoading(false);
      }
    };

    fetchAllocations();  // Call the function to fetch data
  }, []);  // Empty dependency array means this runs once when component mounts

  if (loading) {
    return <div>Loading...</div>;  // Display loading message while data is being fetched
  }

  if (error) {
    return <div>{error}</div>;  // Display error message if there's an issue fetching data
  }

  return (
    <main className="w-full h-screen flex justify-center">
      <div className="overflow-x-auto p-4 w-full">
        <table className="min-w-full bg-white border-collapse rounded-lg shadow-lg">
          <thead className="bg-custom-light-blue">
            <tr>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Allocation ID</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Employee ID</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Employee Name</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Allocated Date</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Return Date</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Reason</th>
              {/* <th className="text-sm font-semibold text-gray-600 p-4 text-left">Equipment ID</th> */}
            </tr>
          </thead>
          <tbody>
            {/* Iterate over the allocations array to render rows */}
            {allocations.map((allocation, index) => (
              <tr
                key={allocation.employeeId}
                className={`hover:bg-gray-50 ${index !== allocations.length - 1 ? 'border-b border-gray-300' : ''}`}
              >
                <td className="p-2 text-gray-700">{allocation.id}</td>
                <td className="p-2 text-gray-700">{allocation.employeeId}</td>
                <td className="p-2 text-gray-700">{allocation.employeeName}</td>
                <td className="p-2 text-gray-700">{new Date(allocation.allocatedDate).toLocaleString()}</td>
                <td className="p-2 text-gray-700">{new Date(allocation.returnDate).toLocaleString()}</td>
                <td className="p-2 text-gray-700">{allocation.reason}</td>
                {/* <td className="p-2 text-gray-700">{allocation.equipmentId}</td> */}
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  );
};

export default ViewAllocations;
