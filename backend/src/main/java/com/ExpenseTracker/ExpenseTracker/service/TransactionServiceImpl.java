    package com.ExpenseTracker.ExpenseTracker.service;

    import com.ExpenseTracker.ExpenseTracker.dto.TransactionDTO;
    import com.ExpenseTracker.ExpenseTracker.exception.ResourceNotFoundException;
    import com.ExpenseTracker.ExpenseTracker.exception.UnauthorizedAccessException;
    import com.ExpenseTracker.ExpenseTracker.mapper.TransactionMapper;
    import com.ExpenseTracker.ExpenseTracker.model.Category;
    import com.ExpenseTracker.ExpenseTracker.model.Transaction;
    import com.ExpenseTracker.ExpenseTracker.model.User;
    import com.ExpenseTracker.ExpenseTracker.repository.TransactionRepository;
    import com.ExpenseTracker.ExpenseTracker.repository.UserRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class TransactionServiceImpl implements TransactionService{

        private final TransactionRepository transactionRepository;
        private final UserRepository userRepository;
        private final TransactionMapper transactionMapper;

        @Autowired
        public TransactionServiceImpl(TransactionRepository transactionRepository,
                                      UserRepository userRepository,
                                      TransactionMapper transactionMapper)
        {
            this.transactionRepository = transactionRepository;
            this.transactionMapper = transactionMapper;
            this.userRepository = userRepository;
        }
        private User getUser(String username) {
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        }

        @Override
        public TransactionDTO createTransaction(String username, TransactionDTO dto) {
            User user = getUser(username);
            Transaction transaction = transactionMapper.newEntity(dto, user);
            return transactionMapper.newDto(transactionRepository.save(transaction));
        }

        @Override
        public List<TransactionDTO> getAllTransactions(String username) {
            User user = getUser(username);
            return transactionRepository.findByUser(user).stream().map(transactionMapper::newDto).collect(Collectors.toList());
        }

        @Override
        public List<TransactionDTO> getTransactionsByCategory(String username, Category category) {
            User user = getUser(username);
            return transactionRepository.findByUserAndCategory(user,category).stream().map(transactionMapper::newDto).collect(Collectors.toList());
        }

        @Override
        public List<TransactionDTO> getTransactionsBetweenDates(String username, LocalDateTime from, LocalDateTime to) {
            return transactionRepository.findByUserAndCreatedAtBetween(getUser(username),from,to).stream().map(transactionMapper::newDto).collect(Collectors.toList());
        }

        @Override
        public List<TransactionDTO> getTransactionByCategoryBetweenDates(String username, Category category, LocalDateTime from, LocalDateTime to) {
            return transactionRepository.findByUserAndCategoryAndCreatedAtBetween(getUser(username),category ,from,to).stream().map(transactionMapper::newDto).collect(Collectors.toList());
        }

        @Override
        public List<TransactionDTO> getTopNTransactions(String username, int n, boolean ascending) {
            List<Transaction> transactions;
            if (ascending)
            {
                transactions = transactionRepository.findTopNByAmtAsc(getUser(username).getId(),n);
            }
            else {
                transactions = transactionRepository.findTopNByAmtDesc(getUser(username).getId(),n);
            }
            return transactions.stream().map(transactionMapper::newDto).collect(Collectors.toList());
        }

        @Override
        public Category getHighestLowestSpendingCategory(String username, boolean high) {
            List<Object[]> result;
            User user = getUser(username);
            Long userId = user.getId();
            if (high) {
                result = transactionRepository.findSpendingPerCategoryDesc(userId);
            }
            else {
                result = transactionRepository.findSpendingPerCategoryAsc(userId);
            }

            if (result.isEmpty()) {
                new ResourceNotFoundException("error");
            }

            Object[] firstRow = result.get(0);

            Category category = (Category) firstRow[0];
            Double totalAmount = (Double) firstRow[1]; // optional, use if needed
            return category;
        }

        @Override
        public TransactionDTO updateTransaction(String username, Long transactionId, TransactionDTO dto) {
            User user = getUser(username);
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found: ID " + transactionId));
            if (!transaction.getUser().getId().equals(user.getId())) {
                throw new UnauthorizedAccessException("Unauthorized to update this transaction");
            }
            return transactionMapper.newDto(
                    transactionRepository.save(
                            transactionMapper.updateEntity(dto,transaction)
                    )
            );
        }

        @Override
        public void deleteTransaction(String username, Long transactionId) {
            User user =getUser(username);
            Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found: ID " + transactionId));
            if (!transaction.getUser().getId().equals(user.getId())) {
                throw new UnauthorizedAccessException("Unauthorized to delete this transaction");
            }

            transactionRepository.delete(transaction);
        }
    }
