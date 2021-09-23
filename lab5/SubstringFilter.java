/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        _input = input;
        _colName = colName;
        _subString = subStr;
    }

    @Override
    protected boolean keep() {
        //filters out any TableRows where the data for the given
        // column does not contain the given substring.
        // Hint: the String contains method) will be helpful
        // for this TableFilter.
        int colIndex = _input.colNameToIndex(_colName);
        if (_next.getValue(colIndex).contains(_subString)){
            return true;
        }
        return false;
    }

    public Table _input;
    public String _colName, _subString;
}
