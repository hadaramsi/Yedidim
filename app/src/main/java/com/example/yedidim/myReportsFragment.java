package com.example.yedidim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yedidim.Model.Model;
import com.example.yedidim.Model.Report;

import java.util.LinkedList;
import java.util.List;


public class myReportsFragment extends Fragment {
//    List<Report> myReports = new LinkedList<Report>();     //TODO: put it in the view model


    MyReportsViewModel viewModel;
    View view;
    MyAdapter adapter;

    public myReportsFragment() {
    }
// TODO עצרתי רגע כי אנחנו צריכות לתכנן איך רק הדיווחים של יוזר מסויים יוצגו


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(MyReportsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_reports, container, false);
        viewModel.setUsername(MyProfileFragmentArgs.fromBundle(getArguments()).getUsername());
        Model.getInstance().getUserReportsList(viewModel.getUsername(), new Model.GetUserReportsListener() {
            @Override
            public void onComplete(List<Report> data) {
                viewModel.setMyReports(data);
                adapter.notifyDataSetChanged();
            }
        });
        RecyclerView list = view.findViewById(R.id.myReports_recycler);
        list.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(layoutManager);
        adapter = new MyAdapter();

        list.setAdapter(adapter);
        adapter.setOnItemClickListener(new ReportsListFragment.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Report r = viewModel.getMyReports().get(position);
                ReportsListFragmentDirections.ActionReportsListFragmentToViewReportFragment action = ReportsListFragmentDirections.actionReportsListFragmentToViewReportFragment(viewModel.getUsername(),r.getReportID());
                Navigation.findNavController(view).navigate(action);
            }
        });

        adapter.setOnDeleteClickListener(new OnDeleteClickListener() {
            @Override
            public void OnDeleteClick(int position) {
                Report r = viewModel.getMyReports().get(position);
                    Model.getInstance().deleteReport(r, new Model.deleteReportListener() {
                        @Override
                        public void onComplete() {
                            //TODO: refresh list
                        }
                    });
            }
        });

        adapter.setOnEditClickListener(new OnEditClickListener() {
            @Override
            public void OnEditClick(int position) {
                Report r = viewModel.getMyReports().get(position);
                Model.getInstance().editReport(r, new Model.editReportListener() {
                    @Override
                    public void onComplete() {
                        //TODO: refresh data
                    }
                });
            }
        });


        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private final ReportsListFragment.OnItemClickListener listener;
        private final OnDeleteClickListener deleteListener;
        private final OnEditClickListener editListener;
        TextView problem;
        Button deleteBtn;
        Button editBtn;

        public MyViewHolder(@NonNull View itemView, ReportsListFragment.OnItemClickListener listener,OnDeleteClickListener deleteListener,OnEditClickListener editListener) {
            super(itemView);
            problem = itemView.findViewById(R.id.myReports_row_tv_problem);
            deleteBtn = itemView.findViewById(R.id.myReports_row_btn_delete);
            editBtn = itemView.findViewById(R.id.myReports_row_btn_edit);
            this.listener = listener;
            this.deleteListener = deleteListener;
            this.editListener = editListener;


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: to check if needed here new list
                    int pos = getAdapterPosition();
                    deleteListener.OnDeleteClick(pos);
                }
            });

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    editListener.OnEditClick(pos);
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(listener != null)
                        listener.onItemClick(pos);
                }
            });
        }
        public void bind(Report report) {
            problem.setText(report.getProblem());
        }
    }

    interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnDeleteClickListener{
        void OnDeleteClick(int position);
    }

    public interface OnEditClickListener{
        void OnEditClick(int position);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        private ReportsListFragment.OnItemClickListener listener;
        private OnDeleteClickListener deleteListener;
        private OnEditClickListener editListener;

        public void setOnItemClickListener(ReportsListFragment.OnItemClickListener listener){
            this.listener = listener;
        }

        void setOnDeleteClickListener(OnDeleteClickListener cbListener){
            this.deleteListener = cbListener;
        }

        void setOnEditClickListener(OnEditClickListener cbListener){
            this.editListener = cbListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rowView = getLayoutInflater().inflate(R.layout.my_reports_row,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(rowView, listener, deleteListener, editListener);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Report report = viewModel.getMyReports().get(position);
            holder.bind(report);
        }

        @Override
        public int getItemCount() {
            return viewModel.getMyReports().size();
        }
    }
}