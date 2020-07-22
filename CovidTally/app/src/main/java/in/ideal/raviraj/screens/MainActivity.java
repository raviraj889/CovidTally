package in.ideal.raviraj.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import in.ideal.raviraj.R;
import in.ideal.raviraj.Test;
import in.ideal.raviraj.adapter.CountryListAdapter;
import in.ideal.raviraj.models.CountryModel;
import in.ideal.raviraj.models.FiltersModel;
import in.ideal.raviraj.models.ResponseModel;
import in.ideal.raviraj.networks.APIClient;
import in.ideal.raviraj.networks.ApiInterface;
import in.ideal.raviraj.utils.CommonUtils;
import in.ideal.raviraj.utils.Constants;
import in.ideal.raviraj.utils.FilterList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends UtilsActivity implements View.OnClickListener {


    List<CountryModel> oDataList = new ArrayList<>();
    List<CountryModel> mDataList = new ArrayList<>();
    List<CountryModel> cData = new ArrayList<>();
    RecyclerView recyclerView;
    TextView txtCases, txtDeaths, txtRecovered;
    RelativeLayout rlSortName, rlSortTC, rlSortTD, rlSortTR;
    ImageView imgNameSort, imgTCSort, imgTDSort, imgTRSort, imgFilter;
    boolean isLoading = false, isDescending = true;
    private final int PAGE_LIMIT = 20;
    FiltersModel filters;
    String sortType = "TC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        txtCases = findViewById(R.id.txtCases);
        txtDeaths = findViewById(R.id.txtDeaths);
        txtRecovered = findViewById(R.id.txtRecovered);
        rlSortName = findViewById(R.id.rlSortName);
        rlSortTC = findViewById(R.id.rlSortTC);
        rlSortTD = findViewById(R.id.rlSortTD);
        rlSortTR = findViewById(R.id.rlSortTR);
        imgNameSort = findViewById(R.id.imgNameSort);
        imgTCSort = findViewById(R.id.imgTCSort);
        imgTDSort = findViewById(R.id.imgTDSort);
        imgTRSort = findViewById(R.id.imgTRSort);
        imgFilter = findViewById(R.id.imgFilter);

        CountryListAdapter adapter = new CountryListAdapter(MainActivity.this, cData);
        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManagaer);
        recyclerView.setAdapter(adapter);

        showProgress();
        getData();

        initScrollListener();


        rlSortName.setOnClickListener(this);
        rlSortTC.setOnClickListener(this);
        rlSortTD.setOnClickListener(this);
        rlSortTR.setOnClickListener(this);
        imgFilter.setOnClickListener(this);

    }

    private void Fetch() {
        mHandler.postDelayed(runnable, 120000);
    }

    private void getData() {
        try {
            ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
            Call<ResponseModel> call = apiInterface.GetSummary();
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    try {
                        if (response != null && response.body() != null && response.body().getCountries() != null && response.body().getCountries().size() > 0) {
                            mDataList.clear();
                            oDataList.clear();
                            oDataList.addAll(response.body().getCountries());
                            mDataList.addAll(response.body().getCountries());

                            filters = null;
                            imgNameSort.setImageResource(R.drawable.sort);
                            imgTCSort.setImageResource(R.drawable.desc);
                            imgTDSort.setImageResource(R.drawable.sort);
                            imgTRSort.setImageResource(R.drawable.sort);
                            isDescending = true;
                            imgFilter.setImageResource(R.drawable.filter);

                            Collections.sort(mDataList, new Comparator<CountryModel>() {
                                @Override
                                public int compare(CountryModel countryModel, CountryModel t1) {
                                    return Long.compare(t1.getTotalConfirmed(), countryModel.getTotalConfirmed());
                                }
                            });
                            PrepareList();
                        } else {
                            showMessgae(response.message());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showMessgae("Something went wrong.\nPlease try again later.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    dismissProgress();
                    showMessgae(t.getMessage(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void PrepareList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cData.clear();
                int count = 0;
                int total_cases = 0, total_deaths = 0, total_recovered = 0;
                for (CountryModel cm : mDataList) {
                    if (cm.getCountry().equalsIgnoreCase(CommonUtils.getString(MainActivity.this, Constants.USERS_COUNTRY))) {
                        cData.add(0, cm);
                    }
                    if (cm.getTotalConfirmed() > 0) {
                        total_cases = total_cases + cm.getTotalConfirmed();
                        total_deaths = total_deaths + cm.getTotalDeaths();
                        total_recovered = total_recovered + cm.getTotalRecovered();
                        if (!cData.contains(cm) && cData.size() < PAGE_LIMIT) {
                            cData.add(cm);
                        }
                    }
                }

                Message message = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("TC", total_cases);
                bundle.putInt("TD", total_deaths);
                bundle.putInt("TR", total_recovered);
                message.setData(bundle);
                message.what = 0;
                mHandler.sendMessage(message);


                mHandler.sendEmptyMessage(1);

            }
        }).start();
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mHandler != null && !isFinishing()) {
                mHandler.removeCallbacks(runnable);
                getData();
            }
        }
    };

    Handler mHandler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0:
                    int tc = msg.getData().getInt("TC");
                    int td = msg.getData().getInt("TD");
                    int tr = msg.getData().getInt("TR");

                    txtCases.setText("" + tc);
                    txtDeaths.setText("" + td);
                    txtRecovered.setText("" + tr);

                    dismissProgress();
                    Fetch();


                    break;

                case 1:

                    recyclerView.getAdapter().notifyDataSetChanged();

                    break;


                case 2:

                    showMessgae("No Data found for applied filters");

                    break;

                default:
                    break;
            }

        }
    };


    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == cData.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        cData.add(null);
        recyclerView.post(new Runnable() {
            public void run() {
                recyclerView.getAdapter().notifyItemInserted(cData.size() - 1);
            }
        });


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cData.remove(cData.size() - 1);
                int scrollPosition = cData.size();
                recyclerView.getAdapter().notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + PAGE_LIMIT;

                int count = 0;
                List<CountryModel> tempList = new ArrayList<>();
                try {
                    for (CountryModel countryModel : mDataList) {
                        if (currentSize - 1 < nextLimit && mDataList.indexOf(countryModel) >= currentSize) {
                            if (count <= PAGE_LIMIT) {
                                tempList.add(countryModel);
                                count++;
                            }
                            currentSize++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cData.addAll(tempList);

                recyclerView.getAdapter().notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rlSortName:

                sortType = "NAME";

                imgTCSort.setImageResource(R.drawable.sort);
                imgTDSort.setImageResource(R.drawable.sort);
                imgTRSort.setImageResource(R.drawable.sort);
                imgNameSort.setImageResource(isDescending ? R.drawable.asc : R.drawable.desc);
                if (isDescending) {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return countryModel.getCountry().compareTo(t1.getCountry());
                        }
                    });
                } else {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return t1.getCountry().compareTo(countryModel.getCountry());
                        }
                    });
                }
                isDescending = isDescending ? false : true;
                PrepareList();

                break;


            case R.id.rlSortTC:

                sortType = "TC";

                imgTCSort.setImageResource(isDescending ? R.drawable.asc : R.drawable.desc);
                imgTDSort.setImageResource(R.drawable.sort);
                imgTRSort.setImageResource(R.drawable.sort);
                imgNameSort.setImageResource(R.drawable.sort);
                if (isDescending) {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return Integer.compare(countryModel.getTotalConfirmed(), t1.getTotalConfirmed());
                        }
                    });
                } else {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return Integer.compare(t1.getTotalConfirmed(), countryModel.getTotalConfirmed());
                        }
                    });
                }

                PrepareList();
                isDescending = isDescending ? false : true;
                break;


            case R.id.rlSortTD:

                sortType = "TD";

                imgTCSort.setImageResource(R.drawable.sort);
                imgTDSort.setImageResource(isDescending ? R.drawable.asc : R.drawable.desc);
                imgTRSort.setImageResource(R.drawable.sort);
                imgNameSort.setImageResource(R.drawable.sort);
                if (isDescending) {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return Integer.compare(countryModel.getTotalDeaths(), t1.getTotalDeaths());
                        }
                    });
                } else {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return Integer.compare(t1.getTotalDeaths(), countryModel.getTotalDeaths());
                        }
                    });
                }
                isDescending = isDescending ? false : true;
                PrepareList();

                break;


            case R.id.rlSortTR:

                sortType = "TR";

                imgTCSort.setImageResource(R.drawable.sort);
                imgTDSort.setImageResource(R.drawable.sort);
                imgTRSort.setImageResource(isDescending ? R.drawable.asc : R.drawable.desc);
                imgNameSort.setImageResource(R.drawable.sort);
                if (isDescending) {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return Integer.compare(countryModel.getTotalRecovered(), t1.getTotalRecovered());
                        }
                    });
                } else {
                    Collections.sort(mDataList, new Comparator<CountryModel>() {
                        @Override
                        public int compare(CountryModel countryModel, CountryModel t1) {
                            return Integer.compare(t1.getTotalRecovered(), countryModel.getTotalRecovered());
                        }
                    });
                }
                isDescending = isDescending ? false : true;
                PrepareList();
                break;


            case R.id.imgFilter:
                FiltersFrag frag = new FiltersFrag();
                frag.setInterface(onFilterItems, filters);
                getSupportFragmentManager().beginTransaction().replace(android.R.id.content, frag, "FF").addToBackStack("FF").commit();
                break;

            default:
                break;
        }


    }

    FiltersFrag.OnFilterItems onFilterItems = new FiltersFrag.OnFilterItems() {
        @Override
        public void showMessage(String message) {
            showMessgae(message);
        }

        @Override
        public void reset() {
            filters = null;
            mDataList.clear();
            mDataList.addAll(oDataList);
            imgFilter.setImageResource(R.drawable.filter);
            sortAndBind();
        }

        @Override
        public void filterApplied(FiltersModel filtersModel) {
            filters = filtersModel;
            new FilterList(CommonUtils.getString(MainActivity.this, Constants.USERS_COUNTRY), oDataList, filtersModel, onFilters).start();

        }
    };


    FilterList.OnFilters onFilters = new FilterList.OnFilters() {
        @Override
        public void applyFilter(List<CountryModel> filteredList) {
            mDataList.clear();
            mDataList.addAll(filteredList);
            imgFilter.setImageResource(R.drawable.filter_applied);
            if (mDataList.size() == 0) {
                mHandler.sendEmptyMessage(2);
            }

            sortAndBind();
        }
    };

    private void sortAndBind() {
        if (sortType == "NAME") {
            if (!isDescending) {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return t1.getCountry().compareTo(countryModel.getCountry());
                    }
                });
            } else {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return countryModel.getCountry().compareTo(t1.getCountry());
                    }
                });
            }
        } else if (sortType == "TC") {
            if (!isDescending) {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return Integer.compare(countryModel.getTotalConfirmed(), t1.getTotalConfirmed());
                    }
                });
            } else {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return Integer.compare(t1.getTotalConfirmed(), countryModel.getTotalConfirmed());
                    }
                });
            }
        } else if (sortType == "TD") {
            if (!isDescending) {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return Integer.compare(countryModel.getTotalDeaths(), t1.getTotalDeaths());
                    }
                });
            } else {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return Integer.compare(t1.getTotalDeaths(), countryModel.getTotalDeaths());
                    }
                });
            }
        } else if (sortType == "TR") {
            if (!isDescending) {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return Integer.compare(countryModel.getTotalRecovered(), t1.getTotalRecovered());
                    }
                });
            } else {
                Collections.sort(mDataList, new Comparator<CountryModel>() {
                    @Override
                    public int compare(CountryModel countryModel, CountryModel t1) {
                        return Integer.compare(t1.getTotalRecovered(), countryModel.getTotalRecovered());
                    }
                });
            }
        }

        PrepareList();
    }

}
