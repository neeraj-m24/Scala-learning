import Header from './components/common/Header'
import { Outlet } from 'react-router-dom'

function App() {

  return (
    <div className='bg-gray'>
      <Header/>
      <Outlet/>
    </div>
  )
}

export default App


