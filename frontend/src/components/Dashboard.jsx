import { useEffect, useState } from "react";
import axios from "../api/axios";
import AddExpenseForm from "./AddExpenseForm";

const Dashboard = () => {
  const [transactions, setTransactions] = useState([]);
  const [showForm, setShowForm] = useState(false);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const response = await axios.get("/transactions");
        setTransactions(response.data);
      } catch (err) {
        console.error("Failed to fetch transactions:", err);
      }
    };

    fetchTransactions();
  }, []);

  return (
    <div>
      <h2>Dashboard</h2>
      <button onClick={() => setShowForm(!showForm)}>
        {showForm ? "Close Form" : "Add Expense"}
      </button>

      {showForm && <AddExpenseForm onClose={() => setShowForm(false)} />}

      <table border="1" cellPadding="6" style={{ marginTop: "20px" }}>
        <thead>
          <tr>
            <th>Date</th>
            <th>Amount</th>
            <th>Category</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          {transactions.map((t) => (
            <tr key={t.transactionId}>
              <td>{new Date(t.createdAt).toLocaleDateString()}</td>
              <td>${t.amount}</td>
              <td>{t.category}</td>
              <td>{t.description}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default Dashboard;
