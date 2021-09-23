/**
 * TableFilter to filter for entries greater than a given string.
 *
 * @author Matthew Owen
 */
public class GreaterThanFilter extends TableFilter {

    public GreaterThanFilter(Table input, String colName, String ref) {
        super(input);
        _input = input;
        _colName = colName;
        _ref = ref;

    }

    @Override
    protected boolean keep() {
        //filters out any TableRows where the data for the given column
        // is not greater than the given string
        int colIndex = _input.colNameToIndex(_colName);
        if (_next.getValue(colIndex).length() > _ref.length()) {
            return true;
        }
        return false;
    }

    public Table _input;
    public String _colName, _ref;
}
