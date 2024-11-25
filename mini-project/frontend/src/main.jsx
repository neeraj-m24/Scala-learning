import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { createBrowserRouter, RouterProvider } from 'react-router-dom'
import AddEquipment from './components/equipments/AddEquipment.jsx'
import AllocateEquipment from './components/equipments/AllocateEquipment.jsx'
import ViewEquipments from './components/equipments/ViewEquipment.jsx'
import ViewAllocations from './components/equipments/ViewAllocations.jsx'
import Home from './components/equipments/Home.jsx'
import ReturnEquipment from './components/equipments/ReturnEquipment.jsx'
const router = createBrowserRouter([
  {
    path:'',
    element:<App/>,
    children:[
      {
        path:'/',
        element:<Home/>
      }
      ,
      {
        path:"/add-equipment",
        element:<AddEquipment/>
      },
      {
        path:"/allocate-equipment",
        element:<AllocateEquipment/>
      },
      {
        path:"/view-equipments",
        element: <ViewEquipments/>
      },
      {
        path:"/view-allocations",
        element:<ViewAllocations/>
      },
      {
        path:'return',
        element:<ReturnEquipment/>
      }
    ]
  }
])
createRoot(document.getElementById('root')).render(
  <StrictMode>
    {/* <App /> */}
    <RouterProvider router={router}/>
  </StrictMode>,
)
