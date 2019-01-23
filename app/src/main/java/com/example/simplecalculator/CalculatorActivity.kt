package com.example.simplecalculator


import android.app.ActionBar
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import com.example.R
import kotlinx.android.synthetic.main.activity_calculator.*

const val EXTRA = "com.example.SimpleCalculator.MESSAGE"


@Suppress("DEPRECATION")
class CalculatorActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var inputOne = -1
    private var inputTwo = -1
    var res = -99999999
    private var ops: Operation? = Operation.ADD
    var resList = arrayListOf<Int>()

    private var adapter = RvResultAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Calculator"

        val layoutManager = LinearLayoutManager(this)
        rvResult.layoutManager = layoutManager
        rvResult.setHasFixedSize(true)


        val onClick = View.OnClickListener { v ->
            res = 0
            ops = when (v.id) {
                R.id.rbAdd -> Operation.ADD
                R.id.rbSub -> Operation.SUB
                R.id.rbMul -> Operation.MUL
                R.id.rbDiv -> Operation.DIV
                else -> throw IllegalStateException("not an expected operation")
            }
        }





        rbAdd.setOnClickListener(onClick)
        rbSub.setOnClickListener(onClick)
        rbMul.setOnClickListener(onClick)
        rbDiv.setOnClickListener(onClick)

        btnToastResult.setOnClickListener {
            compute(ops)
            if (checkRadio() && res != -99999999) {
                Toast.makeText(this, res.toString(), Toast.LENGTH_LONG).show()
                adapter.addResult(res)
                rvResult.adapter = adapter
                resList = adapter.results
                res = -99999999
            }
            rgOperation.clearCheck()
        }

        btnQuickResult.setOnClickListener {
            compute(ops)
            findViewById<TextView>(R.id.tvTextResult).apply {
                if ((!checkRadio()) && res == -99999999)
                    text = " "
                else if (checkRadio() && res != -99999999) {
                    text = res.toString()
                    adapter.addResult(res)
                    rvResult.adapter = adapter
                    resList = adapter.results
                    res = -99999999
                }
            }
            rgOperation.clearCheck()
        }

        btnResult.setOnClickListener {

            compute(ops)
            val intent: Intent?

            if (checkRadio() && res != -99999999) {
                intent = Intent(this, DisplayResultActivity::class.java).apply {
                    putExtra(EXTRA, (res).toString())
                    res = -99999999
                }
                startActivity(intent)
            }
        }

        etSearchBar.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != "")
                    filterSearch(s.toString().toInt())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                adapter.filterResults(resList)
                if (s.toString().isNotEmpty()) {
                    filterSearch(s.toString().toInt())
                }
            }

        })

        linearLayout.setOnClickListener {
            (this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(this.currentFocus.windowToken, 0)
        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onQueryTextChange(query: String?): Boolean {
        adapter.filterResults(resList)
        if (query.toString().isNotEmpty()) {
            filterSearch(query.toString().toInt())
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            menuInflater.inflate(R.menu.search_item, this)
            val menuItem = findItem(R.id.search)
            val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
            searchView.setOnQueryTextListener(this@CalculatorActivity)
            (menuItem.actionView as SearchView).inputType = InputType.TYPE_CLASS_NUMBER
            menuItem.setOnMenuItemClickListener {
                searchView.setIconifiedByDefault(false)
                searchView.layoutParams =
                        ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT)
                this.findItem(R.id.search).expandActionView()
            }
        }

        return true

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.run {
            this.putIntegerArrayList("resultlist", resList)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.run {

            getIntegerArrayList("resultlist")?.let {
                resList = ArrayList(it.toList())
            }

            adapter.results = resList
            rvResult.adapter = adapter
        }
    }

    fun filterSearch(number: Int) {
        val list = arrayListOf<Int>()
        val resultList = ArrayList<Int>(adapter.results)

        for (i in 0 until resultList.size) {
            if (resultList[i] <= number)
                list.add(resultList[i])
        }

        adapter.filterResults(list)
    }

    private fun compute(opr: Operation?) {
        if (etOne.text.toString() == "" || etTwo.text.toString() == "") {
            Snackbar.make(linearLayout, "Enter the numbers", Snackbar.LENGTH_LONG).show()
            res = -99999999
            return
        }

        inputOne = etOne.text.toString().toInt()
        inputTwo = etTwo.text.toString().toInt()


        when (opr) {
            Operation.ADD -> res = inputOne + inputTwo
            Operation.SUB -> res = inputOne - inputTwo
            Operation.MUL -> res = inputOne * inputTwo
            Operation.DIV -> try {
                res = inputOne / inputTwo
            } catch (e: ArithmeticException) {
                this.ops = null
                Snackbar.make(linearLayout, "Cannot Divide binputTwozero", Snackbar.LENGTH_LONG).show()
                rgOperation.clearCheck()
                findViewById<TextView>(R.id.tvTextResult).apply {
                    text = ""
                }
            }
        }
    }

    private fun checkRadio(): Boolean {
        if (rbAdd.isChecked || rbSub.isChecked || rbDiv.isChecked || rbMul.isChecked)
            return true
        return false
    }

    enum class Operation {
        ADD, SUB, MUL, DIV
    }
}