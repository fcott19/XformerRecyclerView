package com.fcott.xformerrecyclerview;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.fcott.xformerrecyclerview.transformer.DepthPageTransformer;
import com.fcott.xformerrecyclerview.transformer.RotatePageTransformer;
import com.fcott.xformerrecyclerview.transformer.SnapPageTransformer;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private XformerRecyclerView recyclerView;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (XformerRecyclerView) this.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setPageTransformer(new SnapPageTransformer());

        radioGroup = (RadioGroup)findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_snap:
                        recyclerView.setPageTransformer(new SnapPageTransformer());
                        break;
                    case R.id.rb_rotate:
                        recyclerView.setPageTransformer(new RotatePageTransformer());
                        break;
                    case R.id.rb_depth:
                        recyclerView.setPageTransformer(new DepthPageTransformer());
                        break;
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_recycler, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.iv.setImageResource(R.mipmap.ic_launcher);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ImageView iv;
            public ViewHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.iv);
            }
        }
    }

}
