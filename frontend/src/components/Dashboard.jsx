import { useEffect, useState } from "react";
import axios from "../api/axios";
import AddExpenseForm from "./AddExpenseForm";
import EditExpenseForm from "./EditExpenseForm";
import { Pie } from "react-chartjs-2";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend
} from "chart.js";

ChartJS.register(ArcElement, Tooltip, Legend);

const Dashboard = () => {
  const [transactions, setTransactions] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editTransaction, setEditTransaction] = useState(null);
  const [category, setCategory] = useState("GROCERIES");
  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");
  const [topN, setTopN] = useState(5);
  const [ascending, setAscending] = useState(false);
  const [topCategoryLabel, setTopCategoryLabel] = useState("");

  const fetchAll = async () => {
    try {
      const res = await axios.get("/transactions");
      setTransactions(res.data);
    } catch (err) {
      console.error("Failed to fetch transactions:", err);
      window.location.href = "/login";
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

  const fetchTopCategory = async (asc) => {
    try {
      const res = await axios.get(`/transactions/top-category?ascending=${asc}`);
      const label = asc ? "Lowest" : "Highest";
      setTopCategoryLabel(`${label} Spending Category: ${res.data}`);
    } catch (err) {
      console.error("Error fetching top category:", err);
      alert("Error fetching top category");
    }
  };

  const handleDelete = async (id) => {
    if (window.confirm("Are you sure you want to delete this transaction?")) {
      try {
        await axios.delete(`/transactions/${id}`);
        fetchAll();
      } catch (err) {
        alert("Failed to delete transaction");
      }
    }
  };

  const totalExpense = transactions.reduce((acc, t) => acc + t.amount, 0);

  const chartData = {
    labels: [...new Set(transactions.map(t => t.category))],
    datasets: [
      {
        data: [...new Set(transactions.map(t => t.category))].map(cat =>
          transactions.filter(t => t.category === cat).reduce((sum, t) => sum + t.amount, 0)
        ),
        backgroundColor: [
          "#FF6384",
          "#36A2EB",
          "#FFCE56",
          "#4BC0C0",
          "#9966FF",
          "#FF9F40",
          "#C9CBCF",
          "#B8D9D9",
          "#A4B1C0"
        ],
      },
    ],
  };

  useEffect(() => {
    fetchAll();
  }, []);

  return (
    <div>
      <h2>Dashboard</h2>
      <button onClick={() => setShowForm(true)}>Add New Expense</button>
      {showForm && <AddExpenseForm onClose={() => setShowForm(false)} />}
      {editTransaction && (
        <EditExpenseForm
          transaction={editTransaction}
          onClose={() => setEditTransaction(null)}
          onUpdate={fetchAll}
        />
      )}

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
        <button onClick={fetchAll}>All</button>
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

      <h4>Total Expense: ${totalExpense.toFixed(2)}</h4>

      <table border="1">
        <thead>
          <tr>
            <th>ID</th>
            <th>Amount</th>
            <th>Category</th>
            <th>Description</th>
            <th>Created At</th>
            <th>Actions</th>
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
              <td>
                <button onClick={() => setEditTransaction(t)}>Edit</button>
                <button onClick={() => handleDelete(t.transactionId)}>Delete</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <hr />
      <h4>Top Category</h4>
      <button onClick={() => fetchTopCategory(false)}>Highest Spending</button>
      <button onClick={() => fetchTopCategory(true)}>Lowest Spending</button>
      <p>{topCategoryLabel}</p>

      <hr />
      <h4>Expense Distribution</h4>
      <Pie data={chartData} />
    </div>
  );
};

export default Dashboard;
