/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {
    /*
    construction passes in two colNames but
    we need to filker out any TableRows (the rows)
    where the data for each of these two columns
    is not matching
     */
    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        /*
        Access the TableRow's at col names and check if they
        are equal if they
         */
        _colName1 = colName1;
        _colName2 = colName2;
        _input = input;
    }

    @Override
    protected boolean keep() {
        // returns true if and only if the value of _next stored in the
        // TableFilter should be delivered by the next() method
        //colnametoindex get the index of the columns
        //get the tablerow with rowIndex then get value from the table row
        int col1Ind = _input.colNameToIndex(_colName1);
        int col2Ind = _input.colNameToIndex(_colName2);
        if (_next.getValue(col1Ind).equals(_next.getValue(col2Ind))){
            return true;
        }
        return false;
    }

    public Table _input;
    public String _colName1;
    public String _colName2;
    // FIXME: Add instance variables?
}
