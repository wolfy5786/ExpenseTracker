import { useEffect, useState } from "react";
import axios from "../api/axios";
import AddExpenseForm from "./AddExpenseForm";

const Dashboard = () => {
  const [transactions, setTransactions] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [category, setCategory] = useState("GROCERIES");
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");
  const [topN, setTopN] = useState(5);
  const [ascending, setAscending] = useState(false);

  const fetchAll = async () => {
    try {
      const res = await axios.get("/transactions");
      setTransactions(res.data);
    } catch (err) {
      console.error("Failed to fetch transactions:", err);
    }
  };

  const fetchByCategory = async () => {
    try {
      const res = await axios.get(`/transactions/category?category=${category}`);
      setTransactions(res.data);
    } catch (err) {
      alert("Error fetching by category");
    }
  };

  const fetchByDateRange = async () => {
    try {
      const res = await axios.get(`/transactions/range?from=${from}&to=${to}`);
      setTransactions(res.data);
    } catch (err) {
      alert("Error fetching by date range");
    }
  };

  const fetchByCategoryAndDate = async () => {
    try {
      const res = await axios.get(`/transactions/category-range?category=${category}&from=${from}&to=${to}`);
      setTransactions(res.data);
    } catch (err) {
      alert("Error fetching by category + date");
    }
  };

  const fetchTopN = async () => {
    try {
      const res = await axios.get(`/transactions/top?n=${topN}&ascending=${ascending}`);
      setTransactions(res.data);
    } catch (err) {
      alert("Error fetching top N");
    }
  };

  useEffect(() => {
    fetchAll();
  }, []);

  return (
    <div>
      <h2>Dashboard</h2>
      <button onClick={() => setShowForm(true)}>Add New Expense</button>
      {showForm && <AddExpenseForm onClose={() => setShowForm(false)} />}

      <hr />

      <div>
        <h4>Filters</h4>

        <label>Category: </label>
        <select value={category} onChange={(e) => setCategory(e.target.value)}>
          <option>GROCERIES</option>
          <option>TRAVEL</option>
          <option>RESTAURANTS</option>
          <option>RENT</option>
          <option>CLEANING</option>
          <option>UTILITIES</option>
          <option>HEALTH</option>
          <option>SUBSCRIPTIONS</option>
          <option>MISCELLANEOUS</option>
        </select>

        <br />

        <label>From: </label>
        <input type="datetime-local" value={from} onChange={(e) => setFrom(e.target.value)} />
        <label>To: </label>
        <input type="datetime-local" value={to} onChange={(e) => setTo(e.target.value)} />

        <br />
        <button onClick={fetchByCategory}>Filter by Category</button>
        <button onClick={fetchByDateRange}>Filter by Date Range</button>
        <button onClick={fetchByCategoryAndDate}>Filter by Category + Date</button>

        <br />
        <label>Top N: </label>
        <input type="number" value={topN} onChange={(e) => setTopN(e.target.value)} />
        <label>
          <input
            type="checkbox"
            checked={ascending}
            onChange={(e) => setAscending(e.target.checked)}
          /> Ascending
        </label>
        <button onClick={fetchTopN}>Get Top N</button>
      </div>

      <hr />

      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Amount</th>
            <th>Category</th>
            <th>Description</th>
            <th>Created At</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((t) => (
            <tr key={t.transactionId}>
              <td>{t.transactionId}</td>
              <td>{t.amount}</td>
              <td>{t.category}</td>
              <td>{t.description}</td>
              <td>{t.createdAt}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Dashboard;
