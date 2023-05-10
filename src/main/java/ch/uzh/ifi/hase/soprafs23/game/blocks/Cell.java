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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Cell otherCell = (Cell) obj;
        return this.status == otherCell.status;
    }
}


