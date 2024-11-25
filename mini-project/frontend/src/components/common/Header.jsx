import React from 'react';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <div className="bg-gradient-to-br from-pink-100 to-orange-100 p-2 shadow-xl flex justify-between">
      <div className="logo px-4 py-2 font-bold text-lg border-transparent shadow-xl p-2">
      <Link to="/">Leaf</Link>
      </div>
      <ul className="flex">
        <li className="p-2 cursor-pointer border-transparent hover:border-gray-300 hover:shadow-lg rounded transition-all">
          <Link to="/add-equipment">Add Equipment</Link>
        </li>
        <li className="p-2 cursor-pointer border-transparent hover:border-gray-300 hover:shadow-lg rounded transition-all">
          <Link to="/view-equipments">All Equipment</Link>
        </li>
        <li className="p-2 cursor-pointer border-transparent hover:border-gray-300 hover:shadow-lg rounded transition-all">
          <Link to="/allocate-equipment">Allocate Equipment</Link>
        </li>
        <li className="p-2 cursor-pointer border-transparent hover:border-gray-300 hover:shadow-lg rounded transition-all">
          <Link to="/view-allocations">All Allocations</Link>
        </li>
        <li className="p-2 cursor-pointer border-transparent hover:border-gray-300 hover:shadow-lg rounded transition-all">
          <Link to="/return">Return Equipment</Link>
        </li>
      </ul>
    </div>
  );
};

export default Header;
