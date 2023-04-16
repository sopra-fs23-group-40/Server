package ch.uzh.ifi.hase.soprafs23.game.blocks;


public class Cell {
    private CellStatus status;
    public Cell(CellStatus status) {
        this.status = status;
    }

    public CellStatus getStatus() {
        return status;
    }

    public void setStatus(CellStatus newStatus) {
            this.status = newStatus;
        }

}


