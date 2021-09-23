/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        _input = input;
        _colName = colName;
        _match = match;
    }

    @Override
    protected boolean keep() {
        /*
        filters out any TableRows where the data for the given
         column does not exactly match the given string.
         */
        int colIndex = _input.colNameToIndex(_colName);
        if (_next.getValue(colIndex).equals(_match)){
            return true;
        }
        return false;
    }

    public Table _input;
    public String _colName, _match;
}
