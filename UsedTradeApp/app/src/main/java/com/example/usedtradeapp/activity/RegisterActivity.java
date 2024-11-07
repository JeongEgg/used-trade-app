package com.example.usedtradeapp.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.usedtradeapp.R;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private List<String> selectedCategories;  // 선택된 카테고리 목록
    private LinearLayout linearLayoutCategories; // 카테고리 버튼을 추가할 LinearLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        findViewById(R.id.btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btnShowCategories = findViewById(R.id.btn_show_categories);
        linearLayoutCategories = findViewById(R.id.linear_layout_categories);  // LinearLayout 참조

        selectedCategories = new ArrayList<>();

        btnShowCategories.setOnClickListener(v -> showCategoryDialog());
    }

    private void showCategoryDialog() {
        final List<String> categories = new ArrayList<>();
        categories.add("카테고리 1");
        categories.add("카테고리 2");
        categories.add("카테고리 3");
        categories.add("카테고리 4");

        String[] categoryArray = categories.toArray(new String[0]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("카테고리 선택")
                .setItems(categoryArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 카테고리 선택 시
                        String selectedCategory = categories.get(which);
                        addCategoryToList(selectedCategory);
                    }
                })
                .setCancelable(true)  // 외부 영역 터치 시 대화 상자가 닫히도록 설정
                .show();
    }

    // 선택된 카테고리를 HorizontalScrollView 에 추가
    private void addCategoryToList(String category) {
        if (!selectedCategories.contains(category)) {
            selectedCategories.add(category);

            // LayoutInflater를 사용하여 XML 레이아웃을 인플레이트
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View categoryView = inflater.inflate(R.layout.list_item_categories, null);  // list_item_categories.xml 레이아웃을 인플레이트

            TextView btnCategoryItem = categoryView.findViewById(R.id.btn_category_item);  // TextView로 수정
            btnCategoryItem.setText(category);

            // Exclude 버튼 설정
            Button btnExclude = categoryView.findViewById(R.id.btn_exclude);
            btnExclude.setOnClickListener(v -> {
                // 버튼 클릭 시 해당 카테고리 삭제
                removeCategory(category, categoryView);  // categoryView를 함께 넘겨서 삭제 처리
            });

            // TextView에 클릭 리스너 추가 (이제 버튼이 아니라 텍스트이므로 클릭 이벤트를 TextView에 설정)
            btnCategoryItem.setOnClickListener(v -> {
                // 클릭 시 해당 카테고리 삭제
                removeCategory(category, categoryView);  // categoryView를 함께 넘겨서 삭제 처리
            });

            // LinearLayout에 레이아웃을 추가
            linearLayoutCategories.addView(categoryView);
        } else {
            Toast.makeText(this, "이미 선택된 카테고리입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 카테고리 항목 삭제
    private void removeCategory(String category, View categoryView) {
        selectedCategories.remove(category);

        // LinearLayout에서 해당 categoryView 삭제
        linearLayoutCategories.removeView(categoryView);
    }
}