package in.ideal.raviraj.screens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import in.ideal.raviraj.R;
import in.ideal.raviraj.models.FiltersModel;

public class FiltersFrag extends Fragment implements View.OnClickListener {

    EditText edtTcMax, edtTcMin, edtTdMax, edtTdMin, edtTrMax, edtTrMin;
    OnFilterItems onFilterItems;
    Button btnApply;
    FiltersModel filtersModel;

    public void setInterface(OnFilterItems onFilterItems, FiltersModel filtersModel) {
        this.onFilterItems = onFilterItems;
        this.filtersModel = filtersModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.filters_frag, container, false);

        edtTcMax = v.findViewById(R.id.edtTcMax);
        edtTcMin = v.findViewById(R.id.edtTcMin);
        edtTdMax = v.findViewById(R.id.edtTdMax);
        edtTdMin = v.findViewById(R.id.edtTdMin);
        edtTrMax = v.findViewById(R.id.edtTrMax);
        edtTrMin = v.findViewById(R.id.edtTrMin);
        btnApply = v.findViewById(R.id.btnApply);

        v.findViewById(R.id.rlMain).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        v.findViewById(R.id.imgClose).setOnClickListener(this);
        v.findViewById(R.id.btnReset).setOnClickListener(this);
        btnApply.setOnClickListener(this);

        if(filtersModel != null){
            edtTcMax.setText("" + filtersModel.getTC_MAX());
            edtTcMin.setText("" + filtersModel.getTC_MIN());
            edtTdMax.setText("" + filtersModel.getTD_MAX());
            edtTdMin.setText("" + filtersModel.getTD_MIN());
            edtTrMax.setText("" + filtersModel.getTR_MAX());
            edtTrMin.setText("" + filtersModel.getTR_MIN());
        }

        edtTcMax.addTextChangedListener(TcWatcher);
        edtTcMin.addTextChangedListener(TcWatcher);

        edtTdMax.addTextChangedListener(TdWatcher);
        edtTdMin.addTextChangedListener(TdWatcher);

        edtTrMax.addTextChangedListener(TrWatcher);
        edtTrMin.addTextChangedListener(TrWatcher);


        return v;
    }


    TextWatcher TcWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() > 0) {
                edtTdMax.setEnabled(false);
                edtTdMax.setAlpha(.8f);
                edtTdMin.setEnabled(false);
                edtTdMin.setAlpha(.8f);

                edtTrMax.setEnabled(false);
                edtTrMax.setAlpha(.8f);
                edtTrMin.setEnabled(false);
                edtTrMin.setAlpha(.8f);
            } else {
                if (!edtTdMax.isEnabled()) {
                    edtTdMax.setEnabled(true);
                    edtTdMax.setAlpha(1f);
                }
                if (!edtTdMin.isEnabled()) {
                    edtTdMin.setEnabled(true);
                    edtTdMin.setAlpha(1f);
                }
                if (!edtTrMax.isEnabled()) {
                    edtTrMax.setEnabled(true);
                    edtTrMax.setAlpha(1f);
                }
                if(!edtTrMin.isEnabled()) {
                    edtTrMin.setEnabled(true);
                    edtTrMin.setAlpha(1f);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };


    TextWatcher TdWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() > 0) {
                edtTcMax.setEnabled(false);
                edtTcMax.setAlpha(.8f);
                edtTcMin.setEnabled(false);
                edtTcMin.setAlpha(1f);

                edtTrMax.setEnabled(false);
                edtTrMax.setAlpha(1f);
                edtTrMin.setEnabled(false);
                edtTrMin.setAlpha(1f);
            } else {
                if(!edtTcMax.isEnabled()) {
                    edtTcMax.setEnabled(true);
                    edtTcMax.setAlpha(1f);
                }
                if(!edtTcMax.isEnabled()) {
                    edtTcMin.setEnabled(true);
                    edtTcMin.setAlpha(1f);
                }
                if(!edtTrMax.isEnabled()) {
                    edtTrMax.setEnabled(true);
                    edtTrMax.setAlpha(1f);
                }
                if(!edtTrMin.isEnabled()) {
                    edtTrMin.setEnabled(true);
                    edtTrMin.setAlpha(1f);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    TextWatcher TrWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() > 0) {
                edtTdMax.setEnabled(false);
                edtTdMax.setAlpha(.8f);
                edtTdMin.setEnabled(false);
                edtTdMin.setAlpha(.8f);

                edtTcMax.setEnabled(false);
                edtTcMax.setAlpha(.8f);
                edtTcMin.setEnabled(false);
                edtTcMin.setAlpha(.8f);
            } else {
                if(!edtTdMax.isEnabled()) {
                    edtTdMax.setEnabled(true);
                    edtTdMax.setAlpha(1f);
                }
                if(!edtTdMin.isEnabled()) {
                    edtTdMin.setEnabled(true);
                    edtTdMin.setAlpha(1f);
                }
                if(!edtTcMax.isEnabled()) {
                    edtTcMax.setEnabled(true);
                    edtTcMax.setAlpha(1f);
                }
                if(!edtTcMin.isEnabled()) {
                    edtTcMin.setEnabled(true);
                    edtTcMin.setAlpha(1f);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgClose:
                getFragmentManager().popBackStack();
                break;

            case R.id.btnReset:
                edtTcMax.setText("");
                edtTcMin.setText("");
                edtTdMax.setText("");
                edtTdMin.setText("");
                edtTrMax.setText("");
                edtTrMin.setText("");
                onFilterItems.reset();
                getFragmentManager().popBackStack();
                break;

            case R.id.btnApply:

                FiltersModel filtersModel = new FiltersModel();
                if (edtTcMax.getText().toString().length() > 0) {
                    try {
                        filtersModel.setTC_MAX(Long.parseLong(edtTcMax.getText().toString().trim()));
                    } catch (Exception e) {
                        filtersModel.setTC_MAX(0);
                    }
                }

                if (edtTcMin.getText().toString().length() > 0) {
                    try {
                        filtersModel.setTC_MIN(Long.parseLong(edtTcMin.getText().toString().trim()));
                    } catch (Exception e) {
                        filtersModel.setTC_MIN(0);
                    }
                }

                if (edtTdMax.getText().toString().length() > 0) {
                    try {
                        filtersModel.setTD_MAX(Long.parseLong(edtTdMax.getText().toString().trim()));
                    } catch (Exception e) {
                        filtersModel.setTD_MAX(0);
                    }
                }

                if (edtTdMin.getText().toString().length() > 0) {
                    try {
                        filtersModel.setTD_MIN(Long.parseLong(edtTdMin.getText().toString().trim()));
                    } catch (Exception e) {
                        filtersModel.setTD_MIN(0);
                    }
                }

                if (edtTrMax.getText().toString().length() > 0) {
                    try {
                        filtersModel.setTR_MAX(Long.parseLong(edtTrMax.getText().toString().trim()));
                    } catch (Exception e) {
                        filtersModel.setTR_MAX(0);
                    }
                }

                if (edtTrMin.getText().toString().length() > 0) {
                    try {
                        filtersModel.setTR_MIN(Long.parseLong(edtTrMin.getText().toString().trim()));
                    } catch (Exception e) {
                        filtersModel.setTR_MIN(0);
                    }
                }

                if (filtersModel.getTC_MAX() == 0 && filtersModel.getTC_MIN() == 0 && filtersModel.getTD_MAX() == 0 &&
                        filtersModel.getTD_MIN() == 0 && filtersModel.getTR_MAX() == 0 && filtersModel.getTR_MIN() == 0) {
                    onFilterItems.showMessage("No filters applied");
                } else {
                    onFilterItems.filterApplied(filtersModel);
                    getFragmentManager().popBackStack();
                }

                break;
        }
    }

    public interface OnFilterItems {
        void showMessage(String message);

        void reset();

        void filterApplied(FiltersModel filtersModel);
    }
}
