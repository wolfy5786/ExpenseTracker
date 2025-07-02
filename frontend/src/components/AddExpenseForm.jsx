import { useState } from "react";
import axios from "../api/axios";

const AddExpenseForm = ({ onClose }) => {
  const [amount, setAmount] = useState("");
  const [category, setCategory] = useState("GROCERIES");
  const [description, setDescription] = useState("");

  const categories = [
    "GROCERIES",
    "TRAVEL",
    "RESTAURANTS",
    "RENT",
    "CLEANING",
    "UTILITIES",
    "HEALTH",
    "SUBSCRIPTIONS",
    "MISCELLANEOUS"
  ];

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post("/transactions", { amount, category, description });
      alert("Expense added!");
      onClose();
      window.location.reload(); // refresh dashboard
    } catch (err) {
      console.error("Add failed:", err);
      alert("Error adding transaction");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h4>Add New Expense</h4>
      <input
        type="number"
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
        required
      /><br />
      <select value={category} onChange={(e) => setCategory(e.target.value)} required>
        <option value="">Select Category</option>
        {categories.map(cat => (
          <option key={cat} value={cat}>{cat}</option>
        ))}
      </select><br />
      <input
        type="text"
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      /><br />
      <button type="submit">Add</button>
    </form>
  );
};

export default AddExpenseForm;
