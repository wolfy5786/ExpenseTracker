import { useState } from "react";
import axios from "../api/axios";

const EditExpenseForm = ({ transaction, onClose }) => {
  const [amount, setAmount] = useState(transaction.amount);
  const [category, setCategory] = useState(transaction.category);
  const [description, setDescription] = useState(transaction.description);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`/transactions/${transaction.transactionId}`, {
        amount,
        category,
        description,
      });
      alert("Transaction updated!");
      onClose();
      window.location.reload();
    } catch (err) {
      console.error("Update failed:", err);
      alert("Failed to update transaction.");
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h4>Edit Transaction</h4>
      <input
        type="number"
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
        required
      />
      <br />
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
      <input
        type="text"
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        required
      />
      <br />
      <button type="submit">Update</button>
      <button type="button" onClick={onClose}>Cancel</button>
    </form>
  );
};

export default EditExpenseForm;
