package com.example.animal;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.animal.databinding.AppBarAndContentBinding;
import com.example.animal.databinding.DrawerBinding;
import com.example.animal.databinding.IconLayoutBinding;
import com.example.animal.databinding.RecycleViewItemAnimalBinding;
import java.util.ArrayList;


/**
 * https://developer.android.com/guide/fragments/appbar#java
 * When using fragments, the app bar can be implemented as an ActionBar that is owned by the host activity or a toolbar within your fragment's layout.
 * Ownership of the app bar varies depending on the needs of your app.
 * <p>
 * If all your screens use the same app bar that's always at the top and spans the width of the screen,
 * then you should use a theme-provided action bar hosted by the activity. Using theme app bars helps
 * to maintain a consistent look and provides a place to host option menus and an up button.
 * <p>
 * Use a toolbar hosted by the fragment if you want more control over the size, placement, and animation of the app bar across multiple screens.
 * For example, you might need a collapsing app bar or one that spans only half the width of the screen and is vertically centered.
 */


public class FrgMH001<AnimalType> extends Fragment implements View.OnClickListener {

    Context mContext;

    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    private DrawerBinding drawerBinding;
    private IconLayoutBinding iconLayoutBinding;
    private AppBarAndContentBinding appBarAndContentBinding;
    private RecycleViewItemAnimalBinding recycleViewItemAnimalBinding;
    private ArrayList<com.example.animal.AnimalType> iconList = new ArrayList<>();

    public void setAnimalTypeArrayList(ArrayList<com.example.animal.AnimalType> animalTypeArrayList) {
        this.iconList = animalTypeArrayList;
    }



    /**
     * https://developer.android.com/guide/fragments/appbar#activity-register
     * The app bar is most commonly owned by the host activity.
     * When the app bar is owned by an activity, fragments can interact with the app bar by overriding framework methods that are called during fragment creation.
     * You must inform the system that your app bar fragment is participating in the population of the options menu.
     * To do this, call setHasOptionsMenu(true) in your fragment's onCreate(Bundle) method.
     * <p>
     * setHasOptionsMenu(true) tells the system that your fragment would like to receive menu-related callbacks.
     * When a menu-related event occurs (creation, clicks, and so on), the event handling method is first called on the activity before being called on the fragment.
     * Note that your application logic should not depend on this order. If multiple fragments are hosted by the same activity, each fragment can supply menu options.
     */

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //l???ng file icon_layout.xml v??o view binding
        iconLayoutBinding = IconLayoutBinding.inflate(LayoutInflater.from(mContext), null, false);
        //l???ng file drawer.xml v??o container (container ch??nh l?? th??nh ph???n LinearLayout R.id.ln_main) ???????c truy???n v??o t??? showfrg()
        drawerBinding = DrawerBinding.inflate(inflater, container, false);
        //l???ng file app_bar_and_content.xml v??o LinearLayout R.id.host_content_with_appbar
        appBarAndContentBinding = AppBarAndContentBinding.inflate(inflater, drawerBinding.hostContentWithAppbar, true);
        //l???ng file recycle_view_item_animal.xml v??o LinearLayout R.id.content_frg
        recycleViewItemAnimalBinding = RecycleViewItemAnimalBinding.inflate(LayoutInflater.from(mContext), appBarAndContentBinding.contentFrg, true);

        init();
        View rootView = drawerBinding.getRoot();

        return rootView;
    }

    private void init() {
        toolbar = appBarAndContentBinding.toolBar;
        drawer = drawerBinding.drawer;

        //b???t bu???c ph???i ??p ki???u v??? MainActivity ????? c?? th??? th???c hi???n vi???c thi???t l???p toolbar
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // t???o n??t buger tr??n thanh toolbar
        toggle = new ActionBarDrawerToggle((MainActivity) getActivity(), drawer, R.string.open, R.string.close);
        // set color cho buger button
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(mContext, R.color.white));
        // ?????ng b??? tr???ng th??i c???a buger button
        toggle.syncState();
        // l???ng nghe s??? ki???n click tr??n buger button
        drawer.addDrawerListener(toggle);

        // l???ng nghe s??? ki???n tr??n card view
        drawerBinding.itemSeaCard.setOnClickListener(this);
        drawerBinding.itemMammalCard.setOnClickListener(this);
        drawerBinding.itemBirdCard.setOnClickListener(this);


    }

    // truy???n context v??o fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    /**
     * https://developer.android.com/guide/fragments/appbar#activity-click
     * Every activity and fragment that participates in the options menu is able to respond to touches.
     * Fragment's onOptionsItemSelected() receives the selected menu item as a parameter and returns a boolean to indicate whether or not the touch has been consumed.
     * Once an activity or fragment returns true from onOptionsItemSelected(), no other participating fragments will receive the callback.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // n???u x???y ra s??? ki???n click tr??n bugger button th?? show Navigation
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Handle click event on item menu
    @Override
    public void onClick(View v) {

        // build Recycler view v???i c??c animal icon
        recyclerV(this.iconList);

        switch (v.getId()) {
            case R.id.itemSea_card:
                iconLayoutBinding.icon.setTag(getString(R.string.folder_icon_sea));
                recycleViewItemAnimalBinding.recycleViewIcon.smoothScrollToPosition(27);
                break;

            case R.id.itemMammal_card:
                iconLayoutBinding.icon.setTag(getString(R.string.folder_icon_mammal));
                recycleViewItemAnimalBinding.recycleViewIcon.smoothScrollToPosition(15);
                break;

            case R.id.itemBird_card:
                iconLayoutBinding.icon.setTag(getString(R.string.folder_icon_bird));
                recycleViewItemAnimalBinding.recycleViewIcon.smoothScrollToPosition(1);
                break;

        }
        Toast.makeText(getActivity(), iconLayoutBinding.icon.getTag().toString(), Toast.LENGTH_SHORT).show();
        drawer.closeDrawer(GravityCompat.START);
    }


    private void recyclerV(ArrayList<com.example.animal.AnimalType> animalTypeArrayList) {
        RecyclerAdapterView adapter = new RecyclerAdapterView(mContext, animalTypeArrayList);
        RecyclerView recyclerView = recycleViewItemAnimalBinding.recycleViewIcon;
        recyclerView.removeAllViews();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
}
