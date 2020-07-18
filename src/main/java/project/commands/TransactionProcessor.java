package project.commands;

import project.dao.TransactionDAO;
import project.model.Transaction;

public class TransactionProcessor implements Command {

    private final Transaction transaction;

    public TransactionProcessor(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void execute() {
        TransactionDAO.getInstance().processTransaction(transaction);
    }
}
