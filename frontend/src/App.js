import './App.css';
import React, { useState } from 'react';
import axios from 'axios';

function App() {
  const ListCoins = () => {
    const [amt, setAmt] = useState('');
    const [denoms, setDenoms] = useState('');
    const [coinList, setCoinList] = useState([]);
    const [error, setError] = useState(null);

    const handleCalculateCoins = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/coin?amt=${amt}&denoms=${denoms}`);
        setCoinList(response.data);
        setError(null);
      } catch (error) {
        setError('Failed to calculate coins. Please check your inputs.');
      }
    };

    return (
      <div className="coin-calculator">
        <h2>Calculate Minimum Coins</h2>
        <div className="input-group">
          <label>Amount:</label>
          <input type="number" value={amt} onChange={(e) => setAmt(e.target.value)} />
        </div>
        <div className="input-group">
          <label>Denominations (comma-separated):</label>
          <input type="text" value={denoms} onChange={(e) => setDenoms(e.target.value)} />
        </div>
        <button className="calculate-btn" onClick={handleCalculateCoins}>Calculate</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <h3>Minimum Coins:</h3>
        <ul>
          {coinList.map((coin, index) => (
            <li key={index}>{coin}</li>
          ))}
        </ul>
      </div>
    );
  };

  return <ListCoins />;
}

export default App;