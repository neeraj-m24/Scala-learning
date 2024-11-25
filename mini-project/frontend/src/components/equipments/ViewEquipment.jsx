// import React from 'react';

// const ViewEquipments = () => {
//   // Sample data for table rows
//   const equipments = [
//     {
//       deviceId: "100344",
//       deviceName: "John",
//       status: "Available",
//       category: "Laptop",
//       description: "Latest Chipset2 Pro",
//       image: "https://unsplash.com/photos/silver-macbook-on-white-table-Hin-rzhOdWs",
//     },
//     {
//       deviceId: "100345",
//       deviceName: "MacBook Pro",
//       status: "Maintenance",
//       category: "Laptop",
//       description: "Latest MacBook Pro Model",
//       image: "https://unsplash.com/photos/silver-macbook-on-white-table-Hin-rzhOdWs",
//     },
//     // Add more rows as needed
//   ];

//   return (
//     <main className="w-full h-screen flex justify-center"> 
//     {/* <main className="w-full h-screen flex items-center justify-center bg-gradient-to-t from-primary to-white">  */}
//       <div className="overflow-x-auto p-4 w-full max-w-6xl">
//         <table className="min-w-full bg-white border-collapse rounded-lg shadow-lg">
//           <thead className="bg-custom-light-blue">
//             <tr>
//               <th className="text-sm font-semibold text-gray-600 p-4 text-left">Device ID</th>
//               <th className="text-sm font-semibold text-gray-600 p-4 text-left">Device Name</th>
//               <th className="text-sm font-semibold text-gray-600 p-4 text-left">Status</th>
//               <th className="text-sm font-semibold text-gray-600 p-4 text-left">Category</th>
//               <th className="text-sm font-semibold text-gray-600 p-4 text-left">Description</th>
//               <th className="text-sm font-semibold text-gray-600 p-4 text-left">Image</th>
//             </tr>
//           </thead>
//           <tbody>
//             {/* Iterate over the equipments array to render rows */}
//             {equipments.map((equipment, index) => (
//               <tr
//                 key={equipment.deviceId}
//                 className={`hover:bg-gray-50 ${index !== equipments.length - 1 ? 'border-b border-gray-300' : ''} `}
//               >
//                 <td className="p-2 text-gray-700">{equipment.deviceId}</td>
//                 <td className="p-2 text-gray-700 flex items-center">
//                   <img className="w-6 h-6 rounded-full mr-2" src="https://i.postimg.cc/FR5xjr4g/user.png" alt="User" />
//                   {equipment.deviceName}
//                 </td>
//                 <td className="p-2 text-gray-700">
//                   <span className={`px-3 py-1 text-sm text-primary bg-blue-100 ${equipment.status==='Available'?'fontcolor-tertiary':''} rounded-full`}>
//                     {equipment.status}
//                   </span>
//                 </td>
//                 <td className="p-2 text-gray-700">{equipment.category}</td>
//                 <td className="p-2 text-gray-700">{equipment.description}</td>
//                 <td className="p-2">
//                   <img className="w-12 h-12 rounded-lg" src={equipment.image} alt="Device" />
//                 </td>
//               </tr>
//             ))}
//           </tbody>
//         </table>
//       </div>
//     </main>
//   );
// };

// export default ViewEquipments;


import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ViewEquipments = () => {
  // State to store fetched equipment data
  const [equipments, setEquipments] = useState([]);
  const [loading, setLoading] = useState(true);  // For showing loading state
  const [error, setError] = useState(null); // To handle errors

  // Fetch the data from the API when the component mounts
  useEffect(() => {
    // Define the function to fetch data
    const fetchEquipments = async () => {
      try {
        const response = await axios.get('http://localhost:9000/api/equipments');
        setEquipments(response.data);  // Store fetched data in state
        setLoading(false);  // Set loading to false once data is fetched
      } catch (err) {
        setError('Failed to fetch equipments');
        setLoading(false);
      }
    };

    fetchEquipments();  // Call the function to fetch data
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
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Device ID</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Device Name</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Status</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Category</th>
              <th className="text-sm font-semibold text-gray-600 p-4 text-left">Description</th>
            </tr>
          </thead>
          <tbody>
            {/* Iterate over the equipments array to render rows */}
            {equipments.map((equipment, index) => (
              <tr
                key={equipment.deviceId}
                className={`hover:bg-gray-50 ${index !== equipments.length - 1 ? 'border-b border-gray-300' : ''} `}
              >
                <td className="p-2 text-gray-700">{equipment.deviceId}</td>
                <td className="p-2 text-gray-700 flex items-center">
                  {equipment.name}
                </td>
                <td className="p-2 text-gray-700">
                  <span className={`px-3 py-1 text-sm text-primary bg-blue-100 rounded-full ${equipment.status === 'AVAILABLE' ? 'text-green-700' : equipment.status === 'ALLOCATED' ? 'text-yellow-700' : 'text-red-700'}`}>
                    {equipment.status}
                  </span>
                </td>
                <td className="p-2 text-gray-700">{equipment.category}</td>
                <td className="p-2 text-gray-700">{equipment.description}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </main>
  );
};

export default ViewEquipments;

