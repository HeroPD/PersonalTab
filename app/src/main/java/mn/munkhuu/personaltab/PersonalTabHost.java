package mn.munkhuu.personaltab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Showtime on 9/8/15.
 */
public class PersonalTabHost extends LinearLayout implements PersonalTab.PersonalTabSelected{

    private int primaryColor;
    private int accentColor;
    private int iconColor;
    private int textColor;
    private List<PersonalTab> tabs;
    private PersonalTabListener listener;
    private int currentTab;

    public PersonalTabHost(Context context) {
        this(context, null);
    }

    public PersonalTabHost(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonalTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if(attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.MaterialTabHost, 0, 0);

            try {
                // custom attributes
                primaryColor = a.getColor(R.styleable.MaterialTabHost_primaryColor, Color.parseColor("#009688"));
                accentColor = a.getColor(R.styleable.MaterialTabHost_accentColor,Color.parseColor("#00b0ff"));
                iconColor = a.getColor(R.styleable.MaterialTabHost_iconColor,Color.WHITE);
                textColor = a.getColor(R.styleable.MaterialTabHost_textColor,Color.WHITE);
            } finally {
                a.recycle();
            }
        }
        tabs = new ArrayList<>();
        super.setBackgroundColor(primaryColor);
    }

    public void setPersonalTabListener(PersonalTabListener listener){
        this.listener = listener;
    }

    public void setPrimaryColor(int color) {
        this.primaryColor = color;

        this.setBackgroundColor(primaryColor);

        for(PersonalTab tab : tabs) {
            tab.setPrimaryColor(color);
        }
    }

    public void setAccentColor(int color) {
        this.accentColor = color;

        for(PersonalTab tab : tabs) {
            tab.setAccentColor(color);
        }
    }

    public void setIconColor(int color) {
        this.iconColor = color;

        for(PersonalTab tab : tabs) {
            tab.setIconColor(color);
        }
    }
    public PersonalTab newTab() {
        return new PersonalTab(this.getContext());
    }
    public void addTab(PersonalTab tab) {

        tab.setAccentColor(accentColor);
        tab.setPrimaryColor(primaryColor);
        tab.setIconColor(iconColor);
        tab.setTabListener(this.listener);
        tab.setPosition(tabs.size());
        tab.setSelectListener(this);
        tabs.add(tab);
    }

    public void setSelectedTab(int position) {
        if(position < 0 || position > tabs.size()) {
            throw new RuntimeException("Index overflow");
        } else {
            // tab at position will select, other will deselect
            for(int i = 0; i < tabs.size(); i++) {
                PersonalTab tab = tabs.get(i);

                if(i == position) {
                    tab.selectTab();
                }
                else {
                    tabs.get(i).deSelectTab();
                }
            }

            currentTab = position;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(this.getWidth() != 0 && tabs.size() != 0)
            notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){

        this.removeAllViews();

        int tabWidth = this.getWidth() / tabs.size();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(tabWidth, HorizontalScrollView.LayoutParams.MATCH_PARENT);
        for (PersonalTab t : tabs) {
            this.addView(t.getTabView(), params);
        }
        setSelectedTab(currentTab);

    }

    @Override
    public void seleted(int position) {
        setSelectedTab(position);
    }
}
