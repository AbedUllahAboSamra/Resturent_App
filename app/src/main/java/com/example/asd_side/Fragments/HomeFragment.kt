package com.example.asd_side.Fragments

import android.content.Context
import android.content.res.Resources
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.asd_side.R
import com.example.asd_side.adapters.CategoryPagerAdapter
import com.example.asd_side.adapters.RecAllMalAdapter
import com.example.asd_side.adapters.RecTrindingNowAdapter
import com.example.asd_side.adapters.RecViewMealPlansAdapter
import com.example.asd_side.databinding.FragmentHomeBinding
import com.example.asd_side.model.MealModle
import com.example.asd_side.screen.SplachActivity


class HomeFragment : Fragment() {


    companion object {
        lateinit var categoryAdapter: CategoryPagerAdapter
       lateinit var recMealPlainAdapter : RecViewMealPlansAdapter
        lateinit var mealTrindingNowAdapter : RecTrindingNowAdapter
        lateinit var allMalAdapterAdapter : RecAllMalAdapter
    }



    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoryAdapter = CategoryPagerAdapter(requireContext(), SplachActivity.arrCategorys)


        var arrr = ArrayList<MealModle>()

        for(item in SplachActivity.arrMeal){
            if (item.category =="Meal Plans Made Easy"){
                arrr.add(item)
            }
        }



        recMealPlainAdapter=RecViewMealPlansAdapter(requireContext(),arrr)
        mealTrindingNowAdapter= RecTrindingNowAdapter(requireContext(),SplachActivity.arrMealTrindingNow)
        allMalAdapterAdapter=  RecAllMalAdapter(SplachActivity.arrMeal)
        binding = FragmentHomeBinding.inflate(layoutInflater)


        // start design Code For set Pager

        binding.vpCategoryPager.offscreenPageLimit = 4
        binding.vpCategoryPager.setPageTransformer(true, object : ViewPager.PageTransformer {

            var numberOfStacked: Int = SplachActivity.arrCategorys.size
            var alphaFactor = 1f / numberOfStacked
            var scaleFactor = 0.1f
            var firstPageScale = 0.9f
            var overlapFactor = 0.5f
            var firstOverlapPx = pxToDp(8)


            override fun transformPage(page: View, position: Float) {
                var pageWidth = page.getWidth()
                var pageHeight = page.getHeight()

                if (position < -numberOfStacked || position > numberOfStacked) {

                    ViewCompat.setTranslationX(page, 0f);
                    ViewCompat.setTranslationY(page, 0f);
                    ViewCompat.setScaleX(page, 1f);
                    ViewCompat.setScaleY(page, 1f);

                } else if (position <= 0f) { // first page


                    ViewCompat.setTranslationX(page, -15f);
                    ViewCompat.setTranslationY(page, 0f);
                    ViewCompat.setScaleX(page, firstPageScale.toFloat());
                    ViewCompat.setScaleY(page, firstPageScale.toFloat());

                } else if (position <= 1) { // second page

                    ViewCompat.setTranslationX(page, pageWidth * -position + 45);
                    ViewCompat.setTranslationY(
                        page,
                        -getTranslationY(pageHeight.toFloat(), position)
                    )
                    var scale = getScale(position);
                    ViewCompat.setScaleX(page, scale);
                    ViewCompat.setScaleY(page, scale);

                } else { // staged pages

                    ViewCompat.setTranslationX(page, pageWidth * -position + 100);
                    ViewCompat.setTranslationY(
                        page,
                        -getTranslationY(pageHeight.toFloat(), position)
                    );
                    var scale = getScale(position);
                    ViewCompat.setScaleX(page, scale);
                    ViewCompat.setScaleY(page, scale);
                }
            }

            fun getScale(position: Float): Float {
                return Math.pow((1f - scaleFactor).toDouble(), (position + 1f).toDouble()).toFloat()
            }

            fun getTranslationY(pageHeight: Float, position: Float): Float {
                var overlap = firstOverlapPx * (
                        1f - overlapFactor * Math.pow(
                            (1f - overlapFactor).toDouble(),
                            (position - 1f).toDouble()
                        ));
                var scaledHeight = getScale(position - 1f) * pageHeight;
                return ((pageHeight - scaledHeight) / 2 + overlap).toFloat()
            }

            private fun pxToDp(px: Int): Int {
                return (px / Resources.getSystem().getDisplayMetrics().density).toInt()
            }
        })

        // end design Code For set Pager

        //start assign Code
        binding.vpCategoryPager.adapter = categoryAdapter

        //end assign Code


        // under line text
        binding.btnView.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        binding.btnViewTrendingNow.paintFlags = Paint.UNDERLINE_TEXT_FLAG


        //start code meal recycleView

        binding.RecViewMealPlans.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RecViewMealPlans.adapter = recMealPlainAdapter

        //end code meal recycleView


        // start Code meal trending recycle

        binding.RecViewMealTrindingNow.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.RecViewMealTrindingNow.adapter = mealTrindingNowAdapter

        // end code meal trending


        // start Code meal trending recycle

        binding.RecViewAllMeal.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.RecViewAllMeal.adapter = allMalAdapterAdapter

        // end code meal trending


        return binding.root
    }
}